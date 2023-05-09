package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.ExceptionBusiness;
import una.filesorganizeridoffice.business.services.Security;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

import java.util.HashMap;

public class Business {
    private static HashMap<String, Protocol> errorList;
    public static void startOrganization(WindowInfo info, Boolean isStudent) throws ExceptionBusiness {
        securityProcess(info, isStudent);
    }

    private static void securityProcess(WindowInfo info, Boolean isStudent) throws ExceptionBusiness {
        Protocol securityResponse = Security.verifyInformation(info, isStudent);
        errorList = Security.getErrorList();
        switch (securityResponse){
            case Refused:
                createException();
                break;
            case Accepted:
                break;
            default:
                break;
        }
    }

    private static void createException() throws ExceptionBusiness {
        String errorMessage = "Los siguientes errores impiden continuar: \n";
        for (String key : errorList.keySet()){
            Protocol p = errorList.get(key);
            errorMessage = errorMessage.concat(key).concat(": ").concat(p.getMessage());
        }
        throw new ExceptionBusiness(errorMessage);
    }
}
