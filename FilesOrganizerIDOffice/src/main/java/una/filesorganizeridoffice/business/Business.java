package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.business.services.ExcelManager;
import una.filesorganizeridoffice.business.services.FileManager;
import una.filesorganizeridoffice.business.services.Security;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;
import una.filesorganizeridoffice.business.util.Tools;
import una.filesorganizeridoffice.model.base.UniversityPerson;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
     * Requests List of UniversityPerson Class, required to contain the list of requesters of University Licence.
     */
    private List<UniversityPerson> requests = new LinkedList<>();

    /***
     * Base Constructor
     */
    public Business() {
    }
    /***
     * This method leads the organization process. Passing the information from one service to another to find any issue
     * and to organize the files the best way possible.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws BusinessException with the message to be displayed on screen.
     */
    public boolean startOrganization(WindowInfo info, Boolean isStudent) throws BusinessException {
        try {
            securityProcess(info, isStudent);
            FileManager.prepareFiles(info, isStudent);
            readExcel(info, isStudent);
            for (UniversityPerson request : requests) {
                FileManager.organizeFiles(request, info, isStudent);
                writeExcel(request, info, isStudent);
            }
        } catch (BusinessException e) {
            //createLog(); Save a registry of the errors and results in a file.
            Tools.errorList.clear();
            throw e;
        } catch (XLSerializableException | InvocationTargetException | IllegalAccessException e) {
            throw new BusinessException("No se completó el proceso. Se utilizó un modelo de clase distinta al permitido.");
        }
        Tools.LoggerWriter.createLog();
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
                Tools.LoggerWriter.createLog();
                createException();
                break;
            case Accepted:
                //Update progress bar
                break;
            default:
                break;
        }
    }

    /***
     * This method uses an ExcelManager service to create the requests list.
     * @param info Window Info
     * @param isStudent Boolean
     * @throws BusinessException
     * @throws XLSerializableException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void readExcel(WindowInfo info, Boolean isStudent) throws BusinessException, XLSerializableException, InvocationTargetException, IllegalAccessException {
        //Si es estudiante la idea sería recibir aqui la lista de estudiantes según los row
        xlManager = new ExcelManager(info.getExcelFileUrl());
        Protocol xlBuilding = xlManager.openXL();
        switch (xlBuilding) {
            case Accepted:
                Protocol xlSheetBuilding = xlManager.startWorking();
                //update progress bar
                switch (xlSheetBuilding) {
                    case Accepted:
                        requests = xlManager.getRequests(info.getInitialRow(), info.getFinalRow(), isStudent);
                        //update progress bar
                        break;
                    case Refused:
                        Tools.errorList.put("Lectura de Hoja de Excel de Solicitudes: ", Protocol.Refused);
                        Tools.LoggerWriter.createLog();
                        createException();
                    default:
                        break;
                }
                break;
            case Refused:
                Tools.errorList.put("Lectura de Excel de Solicitudes: ", Protocol.Refused);
                Tools.LoggerWriter.createLog();
                createException();
            default:
                break;
        }
    }

    /**
     * This method uses ExcelManager to create the individual Excel file for each request.
     * @param request the model with information of the person who requested a licence.
     * @param info the info inserted by the user in the window
     * @param isStudent boolean
     */
    private void writeExcel(UniversityPerson request, WindowInfo info, Boolean isStudent) {

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