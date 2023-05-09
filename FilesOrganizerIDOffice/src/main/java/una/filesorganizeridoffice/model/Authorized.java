package una.filesorganizeridoffice.model;

import java.io.Serializable;
import java.util.Date;

public class Authorized extends PersonalData implements Serializable {
    private Date birthDate;
    private String email;

    public Authorized() {
    }

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

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
