package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.model.base.PersonalData;

import java.io.Serializable;
import java.util.Date;

public class UnderAgeStudent extends UniversityPerson implements Serializable {
    private Date birthDate;
    private Authorized authorized;

    public UnderAgeStudent() {
    }

    public UnderAgeStudent(PersonalData p, UniversityPerson uP, Date birthDate, Authorized authorized) {
        super(p, uP);
        this.birthDate = birthDate;
        this.authorized = authorized;
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
