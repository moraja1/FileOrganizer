package una.filesorganizeridoffice.model;

import java.util.Date;

public class Authorized extends PersonalData{
    private Date birthDate;
    private String email;
    public Authorized(PersonalData p, Date birthDate, String email) {
        super(p);
        this.birthDate = birthDate;
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }
}
