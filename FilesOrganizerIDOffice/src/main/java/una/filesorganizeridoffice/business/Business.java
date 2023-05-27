package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.BusinessException;
import una.filesorganizeridoffice.business.services.ExcelManager;
import una.filesorganizeridoffice.business.services.FilePreparer;
import una.filesorganizeridoffice.business.services.Security;
import una.filesorganizeridoffice.business.api.xl.exceptions.XLSerializableException;
import una.filesorganizeridoffice.model.base.UniversityPerson;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Business {
    private HashMap<String, Protocol> errorList;
    private Security security;
    private FilePreparer preparer;
    private ExcelManager xlManager;
    private List<UniversityPerson> requests = new ArrayList<>();
    public Business() {
    }
    /***
     * This method leads the organization process. Passing the information from one service to another to find any issue
     * and to organize the files the best way possible.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws BusinessException with the message to be displayed on screen.
     */
    public void startOrganization(WindowInfo info, Boolean isStudent) throws BusinessException {
        try {
            securityProcess(info, isStudent);
            prepareFilesBeforeOrganizing(info, isStudent);
            readExcel(info, isStudent);
            organizeFiles(info, isStudent);
        } catch (BusinessException e) {
            security = null;
            preparer = null;
            //writeLog(); Save a registry of the errors and results in a file.
            errorList.clear();
            throw e;
        } catch (XLSerializableException | InvocationTargetException | IllegalAccessException e) {
            throw new BusinessException("No se completó el proceso. Se utilizó un modelo de clase distinta al permitido.");
        }
    }
    /***
     * This method executes the Security process and evaluates whether Security accepted it or not.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws BusinessException with the message created by createException method.
     */
    private void securityProcess(WindowInfo info, Boolean isStudent) throws BusinessException {
        security = new Security();
        Protocol securityResponse = security.verifyInformation(info, isStudent);
        errorList = security.getErrorList();
        switch (securityResponse)
        {
            case Refused:
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
     * This method uses other service to prepare all the files before organization.
     * @param info WindowInformation
     * @param isStudent Boolean
     */
    private void prepareFilesBeforeOrganizing(WindowInfo info, Boolean isStudent) {
        preparer = new FilePreparer();
        preparer.prepareFiles(info, isStudent);
        //Update progress Bar
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
                        throw new BusinessException(xlBuilding.getMessage().concat("Lectura de Hoja de Excel de Solicitudes"));
                    default:
                        break;
                }
                break;
            case Refused:
                throw new BusinessException(xlBuilding.getMessage().concat("Lectura de Excel de Solicitudes"));
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
        for (String k : errorList.keySet()){
            Protocol p = errorList.get(k);
            errorMessage = errorMessage.concat(k).concat(": ").concat(p.getMessage());
        }
        throw new BusinessException(errorMessage);
    }

    /***
     * This method manages to create a directory for every request as well as move their required files.
     * @param info
     */
    private void organizeFiles(WindowInfo info, boolean isStudent) {
        List<String> dirWithError = new LinkedList<>();
        for (UniversityPerson r: requests) {
            //Creates directory absolute path
            String dirName = "".concat(r.getName()).concat(" ").concat(r.getMiddleName()).concat(" ").
                    concat(r.getLastName()).concat("-").concat(r.getId_una());
            String dirAbsolutePath = info.getOutputFileUrl().concat("\\").concat(dirName);
            File dir = new File(dirAbsolutePath);
            //Creates directory
            if(!dir.exists()){
                if(dir.mkdir()){
                    //Move required files
                    File pdfDirectory = new File(info.getPdfFileUrl());
                    File[] pdfs = pdfDirectory.listFiles();
                    for (File pdf : pdfs){
                        if(pdf.getName().startsWith(r.getId_una())){
                            System.out.println(pdf.getAbsolutePath());
                        }
                    }
                }else{
                    //Save errors for future logger.
                    //Error are not process, just skipped.
                    dirWithError.add(dirName);
                }
            }
        }
    }
}
