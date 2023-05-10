package una.filesorganizeridoffice.model.base;

import java.io.Serializable;

public class PersonalData implements Serializable {
    private IdentificationType idType;
    private int id;
    private String name;
    private String middleName;
    private String lastName;
    private Gender gender;
    private int phoneNumber;

    public PersonalData() {
    }

    public PersonalData(String name, String middleName, String lastName, IdentificationType idType, Integer id, Gender gender, Integer phoneNumber) {
        this.name = name;
        this.middleName = middleName;
        this.lastName = lastName;
        this.idType = idType;
        this.id = id;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
    }

    protected PersonalData(PersonalData p) {
        this.name = p.getName();
        this.middleName = p.getMiddleName();
        this.lastName = p.getLastName();
        this.idType = p.getIdType();
        this.id = p.getId();
        this.gender = p.getGender();
        this.phoneNumber = p.getPhoneNumber();
        p = null;
    }

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

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setIdType(IdentificationType idType) {
        this.idType = idType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
