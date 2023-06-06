package una.filesorganizeridoffice.model.base;

import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellGetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;
import una.filesorganizeridoffice.business.util.Processes;

import static una.filesorganizeridoffice.model.base.Gender.Feminine;
import static una.filesorganizeridoffice.model.base.Gender.Masculine;
import static una.filesorganizeridoffice.model.base.IdentificationType.*;

@XLSerializable
public abstract class PersonalData {
    protected IdentificationType idType;
    protected Integer id;
    protected String name;
    protected String middleName;
    protected String lastName;
    protected Gender gender;
    protected String phoneNumber;
    @XLCellGetValue({
            @XLCellColumn(processOf = "adultStu", column = "A"),
            @XLCellColumn(processOf = "underAgeStu", column = "D"),
            @XLCellColumn(processOf = "employee", column = "A"),
            @XLCellColumn(processOf = "authorized", column = "N")
    })
    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }
    public String getLastName() {
        return lastName;
    }

    public IdentificationType getIdType() {
        return idType;
    }

    public Integer getId() {
        return id;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "F"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "V"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "G")
    })
    public void setIdType(String idType) {
        if (idType.equals(DIMEX.getValue())) {
            this.idType = DIMEX;
        } else if (idType.equals(Passport.getValue())) {
            this.idType = Passport;
        } else if (idType.equals(DIDI.getValue())) {
            this.idType = DIDI;
        }else{
            this.idType = Cedula;
        }
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "G"),
            @XLCellColumn(processOf = "authorized", column = "W"),
            @XLCellColumn(processOf = "employee", column = "H")
    })
    public void setId(Integer id) {
        this.id = id;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "C"),
            @XLCellColumn(processOf = "authorized", column = "X"),
            @XLCellColumn(processOf = "employee", column = "D")
    })
    public void setName(String name) {
        this.name = name;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "D"),
            @XLCellColumn(processOf = "authorized", column = "Y"),
            @XLCellColumn(processOf = "employee", column = "E")
    })
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "E"),
            @XLCellColumn(processOf = "authorized", column = "Z"),
            @XLCellColumn(processOf = "employee", column = "F")
    })
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "J"),
            @XLCellColumn(processOf = "authorized", column = "AA"),
            @XLCellColumn(processOf = "employee", column = "J")
    })
    public void setGender(String gender) {
        if (gender.equals(Feminine.getValue())){
            this.gender = Feminine;
        }else{
            this.gender = Masculine;
        }
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "I"),
            @XLCellColumn(processOf = "authorized", column = "AC"),
            @XLCellColumn(processOf = "employee", column = "I")
    })
    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = String.valueOf(phoneNumber);
    }
}
