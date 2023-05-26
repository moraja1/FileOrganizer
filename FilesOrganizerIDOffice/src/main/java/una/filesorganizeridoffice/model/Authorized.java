package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;
import una.filesorganizeridoffice.model.base.PersonalData;

import java.time.LocalDate;
@XLSerializable
public class Authorized extends PersonalData {
    private LocalDate birthDate;
    private String email;
    public Authorized() {
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "authorized", column = "AB")
    })
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "authorized", column = "AD")
    })
    public void setEmail(String email) {
        this.email = email;
    }
}
