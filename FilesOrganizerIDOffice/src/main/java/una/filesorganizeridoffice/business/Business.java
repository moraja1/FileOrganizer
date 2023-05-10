package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.ExceptionBusiness;
import una.filesorganizeridoffice.business.services.ExcelReader;
import una.filesorganizeridoffice.business.services.FilePreparer;
import una.filesorganizeridoffice.business.services.Security;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.util.HashMap;

public class Business {
    private HashMap<String, Protocol> errorList;
    private Security security;
    private FilePreparer preparer;
    private ExcelReader excelReader;

    public Business() {
    }

    /***
     * This method leads the organization process. Passing the information from one service to another to find any issue
     * and to organize the files the best way possible.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws ExceptionBusiness with the message to be displayed on screen.
     */
    public void startOrganization(WindowInfo info, Boolean isStudent) throws ExceptionBusiness {
        try {
            securityProcess(info, isStudent);
            prepareFilesBeforeOrganizing(info, isStudent);
            readExcel(info, isStudent);
        } catch (ExceptionBusiness e) {
            security = null;
            preparer = null;
            //writeLog(); Save a registry of the errors and results in a file.
            errorList.clear();
            throw e;
        }

    }

    private void readExcel(WindowInfo info, Boolean isStudent) {
        if(isStudent){

        }
    }

    /***
     * This method executes the Security process and evaluates whether Security accepted it or not.
     * @param info WindowInformation
     * @param isStudent Boolean
     * @throws ExceptionBusiness with the message created by createException method.
     */
    private void securityProcess(WindowInfo info, Boolean isStudent) throws ExceptionBusiness {
        security = new Security();
        Protocol securityResponse = security.verifyInformation(info, isStudent);
        errorList = security.getErrorList();
        switch (securityResponse){
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
     * This method creates the Error Message that will be displayed on the screen.
     * @throws ExceptionBusiness with the message to be displayed on screen.
     */
    private void createException() throws ExceptionBusiness {
        String errorMessage = "Los siguientes errores impiden continuar: \n";
        for (String k : errorList.keySet()){
            Protocol p = errorList.get(k);
            errorMessage = errorMessage.concat(k).concat(": ").concat(p.getMessage());
        }
        throw new ExceptionBusiness(errorMessage);
    }
}
