package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.ExceptionBusiness;
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
            errorMessage = errorMessage.concat(key).concat(": ");
            Protocol p = errorList.get(key);
            switch (p){
                case UrlError:
                    errorMessage = errorMessage.concat("Existe un error en la dirección url.\n");
                    break;
                case CreateDirError:
                    errorMessage = errorMessage.concat("No se pudo crear el directorio.\n");
                    break;
                case OtherFilesError:
                    errorMessage = errorMessage.concat("Existen archivos con otras extensiones.\n");
                    break;
                case FileEmpty:
                    errorMessage = errorMessage.concat("El directorio está vacío.\n");
                    break;
                default:
                    break;
            }
        }
        throw new ExceptionBusiness(errorMessage);
    }
}
