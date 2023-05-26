package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;
import una.filesorganizeridoffice.model.base.UniversityPerson;

import java.time.LocalDate;

@XLSerializable
public class UnderAgeStudent extends UniversityPerson {
    private LocalDate birthDate;
    private Authorized authorized;

    public UnderAgeStudent() {
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Authorized getAuthorized() {
        return authorized;
    }

    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "U")
    })
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setAuthorized(Authorized authorized) {
        this.authorized = authorized;
    }
}
