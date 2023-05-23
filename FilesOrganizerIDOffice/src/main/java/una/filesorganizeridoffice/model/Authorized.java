package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.model.base.PersonalData;

import java.io.Serializable;
import java.util.Date;

public class Authorized extends PersonalData {
    private Date birthDate;
    private String email;

    public Authorized() {
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
