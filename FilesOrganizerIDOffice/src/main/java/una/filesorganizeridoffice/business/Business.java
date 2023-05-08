package una.filesorganizeridoffice.business;

import una.filesorganizeridoffice.business.exceptions.ExceptionBusiness;
import una.filesorganizeridoffice.viewmodel.WindowInfo;

public class Business {
    public static void startOrganization(WindowInfo info, Boolean isStudent) throws ExceptionBusiness {
        Protocol securityResponse = Security.verifyInformation(info, isStudent);
    }
}
