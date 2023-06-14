package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.business.services.ExcelManager;
import una.filesorganizeridoffice.business.services.FileManager;
import una.filesorganizeridoffice.business.services.Security;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;
import una.filesorganizeridoffice.business.util.Tools;
import una.filesorganizeridoffice.model.Adult;
import una.filesorganizeridoffice.model.UnderAgeStudent;
import una.filesorganizeridoffice.model.base.UniversityPerson;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/***
 * Final class that contains all the logical process of the system. This class establishes the step by step to
 * automate the process of organizing and creating files needed.
 * @author Jaison Mora Víquez <a href="https://github.com/moraja1">Github</a>
 */
public final class Business {
    /***
     * Excel manager instance, required to process API.xl
     */
    private ExcelManager xlManager;
    /***
     * This method leads the organization process. Passing the information from one service to another to find any issue
     * and to organize the files the best way possible.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws BusinessException with the message to be displayed on screen.
     */
    public boolean startOrganization(WindowInfo info, Boolean isStudent) throws BusinessException {
        try {
            //Runs Security and Prepare files
            securityProcess(info, isStudent);
            FileManager.prepareFiles(info, isStudent);
            //update progress bar

            //Read Excel requests and get requests
            xlManager = new ExcelManager(info.getExcelFileUrl());
            runExcel(xlManager);
            xlManager.getRequests(info.getInitialRow(), info.getFinalRow(), isStudent);
            //update progress bar

            //Processes requests
            for (UniversityPerson request : Tools.requests) {
                //Creates the ExcelManager for each type of request
                boolean isOk = true;
                if(request instanceof Adult){
                    //String path = App.class.getResource("xlsx/formatAdult.xlsx").getPath();
                    String path = "C:\\Users\\N00148095\\Downloads\\formatAdult.xlsx";
                    xlManager = new ExcelManager(path);
                }else if(request instanceof UnderAgeStudent){
                    //String path = App.class.getResource("xlsx/formatUnderAge.xlsx").getPath();
                    String path = "C:\\Users\\N00148095\\Downloads\\formatAdult.xlsx";
                    xlManager = new ExcelManager(path);
                }else{
                    Tools.errorList.put(request.getId_una(), Protocol.Refused);
                    isOk = false;
                }
                try{
                    if(isOk){
                        //Run Excel Format and creates the Excel
                        runExcel(xlManager);
                        xlManager.createExcel(request, isStudent);
                    }
                    //Organizes files
                    //FileManager.organizeFiles(request, info, isStudent);
                } catch (BusinessException e) {
                    //createLog(); Save a registry of the errors and results in a file.
                    Tools.errorList.put("No se pudo crear el archivo de Excel " + request.getId_una() + ".xlsx: ", Protocol.Refused);
                } catch (XLSerializableException | InvocationTargetException |
                         IllegalAccessException | IOException e) {
                    throw new BusinessException("Se utilizó un modelo de clase distinta al permitido.");
                }
            }
        } catch (BusinessException e) {
            //createLog(); Save a registry of the errors and results in a file.
            Tools.errorList.clear();
            throw e;
        } catch (XLSerializableException | InvocationTargetException | IllegalAccessException e) {
            throw new BusinessException("Se utilizó un modelo de clase distinta al permitido.");
        }
        new Tools.LoggerWriter();
        return true;
    }

    /***
     * This method executes the Security process and evaluates whether Security accepted it or not.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws BusinessException with the message created by createException method.
     */
    private void securityProcess(WindowInfo info, Boolean isStudent) throws BusinessException {
        Protocol securityResponse = Security.verifyInformation(info, isStudent);
        switch (securityResponse)
        {
            case Refused:
                new Tools.LoggerWriter();
                createException();
                break;
            case Accepted:
                //Update progress bar
                break;
            default:
                break;
        }
    }

    /**
     * Executes the operations to load Excel files.
     * @param xlManager ExcelManager is the class responsible for handle any operation related to the Excel files.
     * @throws BusinessException
     */
    private void runExcel(ExcelManager xlManager) throws BusinessException {
        Protocol xlBuilding = xlManager.openXL();
        switch (xlBuilding) {
            case Accepted:
                Protocol xlSheetBuilding = xlManager.startWorking();
                switch (xlSheetBuilding) {
                    case Refused:
                        Tools.errorList.put("Lectura de Hoja de Excel " + xlManager.getXlSheet().getName() + ": ", Protocol.Refused);
                        new Tools.LoggerWriter();
                        createException();
                    default:
                        break;
                }
                break;
            case Refused:
                Tools.errorList.put("Lectura de Excel " + xlManager.getXlWorkbook().getXlName() + ": ", Protocol.Refused);
                new Tools.LoggerWriter();
                createException();
            default:
                break;
        }
    }

    /***
     * This method creates the Error Message that will be displayed on the screen.
     * @throws BusinessException with the message to be displayed on screen.
     */
    private void createException() throws BusinessException {
        String errorMessage = "Los siguientes errores impiden continuar: \n";
        for (String k : Tools.errorList.keySet()){
            Protocol p = Tools.errorList.get(k);
            errorMessage = errorMessage.concat(k).concat(": ").concat(p.getMessage());
        }
        throw new BusinessException(errorMessage);
    }
}