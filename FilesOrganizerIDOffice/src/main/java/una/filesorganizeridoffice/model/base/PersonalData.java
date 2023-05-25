package una.filesorganizeridoffice.model.base;

import una.filesorganizeridoffice.business.xl.annotations.XLBaseSerializable;
import una.filesorganizeridoffice.business.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.xl.annotations.XLCellSetValue;

@XLBaseSerializable
public abstract class PersonalData {
    protected IdentificationType idType;
    protected int id;
    protected String name;
    protected String middleName;
    protected String lastName;
    protected Gender gender;
    protected String phoneNumber;
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
            @XLCellColumn(processOf = "student", column = "F"),
            @XLCellColumn(processOf = "authorized", column = "V")
    })
    public void setIdType(IdentificationType idType) {
        this.idType = idType;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "G"),
            @XLCellColumn(processOf = "authorized", column = "W")
    })
    public void setId(Integer id) {
        this.id = id;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "C"),
            @XLCellColumn(processOf = "authorized", column = "X")
    })
    public void setName(String name) {
        this.name = name;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "D"),
            @XLCellColumn(processOf = "authorized", column = "Y")
    })
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "E"),
            @XLCellColumn(processOf = "authorized", column = "Z")
    })
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "J"),
            @XLCellColumn(processOf = "authorized", column = "AA")
    })
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "I"),
            @XLCellColumn(processOf = "authorized", column = "AC")
    })
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
