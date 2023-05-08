package una.filesorganizeridoffice.model;

import java.util.Date;

public class UnderAgeStudent extends UniversityPerson{
    private Date birthDate;
    private Authorized authorized;

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
}
