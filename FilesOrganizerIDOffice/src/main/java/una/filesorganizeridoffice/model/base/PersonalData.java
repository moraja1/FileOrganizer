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
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "A"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "D"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "A"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "N")
    })
    public String getName() {
        return name;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "B"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "E"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "B"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "O")
    })
    public String getMiddleName() {
        return middleName;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "C"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "F"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "C"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "P")
    })
    public String getLastName() {
        return lastName;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "D"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "A"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "D"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "L")
    })
    public String getIdType() {
        return idType.getValue();
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "E"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "B"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "E"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "M")
    })
    public Integer getId() {
        return id;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "H"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "G"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "H"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "Q")
    })
    public String getGender() {
        return gender.getValue();
    }

    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "G"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "I"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "G"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "S")
    })
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
            @XLCellColumn(processOf = Processes.STUDENT, column = "G"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "W"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "H")
    })
    public void setId(Integer id) {
        this.id = id;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "C"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "X"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "D")
    })
    public void setName(String name) {
        this.name = name;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "D"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "Y"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "E")
    })
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "E"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "Z"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "F")
    })
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "J"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "AA"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "J")
    })
    public void setGender(String gender) {
        if (gender.equals(Feminine.getValue())){
            this.gender = Feminine;
        }else{
            this.gender = Masculine;
        }
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "I"),
            @XLCellColumn(processOf = Processes.AUTHORIZED, column = "AC"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "I")
    })
    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = String.valueOf(phoneNumber);
    }
}
