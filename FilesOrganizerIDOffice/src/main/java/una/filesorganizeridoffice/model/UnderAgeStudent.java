package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.business.xl.annotations.XLSubSerializable;
import una.filesorganizeridoffice.model.base.PersonalData;
import una.filesorganizeridoffice.model.base.UniversityPerson;

import java.io.Serializable;
import java.util.Date;

@XLSubSerializable(processOf = "student")
public class UnderAgeStudent extends UniversityPerson {
    private Date birthDate;
    private Authorized authorized;

    public UnderAgeStudent() {
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public Authorized getAuthorized() {
        return authorized;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setAuthorized(Authorized authorized) {
        this.authorized = authorized;
    }
}
