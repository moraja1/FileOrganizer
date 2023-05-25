package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.business.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.xl.annotations.XLSubSerializable;
import una.filesorganizeridoffice.model.base.IdentificationType;
import una.filesorganizeridoffice.model.base.PersonalData;

import java.util.Date;
@XLSubSerializable(processOf = "authorized")
public class Authorized extends PersonalData {
    private Date birthDate;
    private String email;
    protected IdentificationType idType;
    protected int id;
    public Authorized() {
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "authorized", column = "AB")
    })
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "authorized", column = "AD")
    })
    public void setEmail(String email) {
        this.email = email;
    }
}
