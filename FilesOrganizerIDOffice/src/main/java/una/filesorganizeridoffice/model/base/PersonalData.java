package una.filesorganizeridoffice.model.base;

import java.io.Serializable;

public abstract class PersonalData {
    protected IdentificationType idType;
    protected int id;
    protected String name;
    protected String middleName;
    protected String lastName;
    protected Gender gender;
    protected String phoneNumber;

    protected String getName() {
        return name;
    }

    protected String getMiddleName() {
        return middleName;
    }

    protected String getLastName() {
        return lastName;
    }

    protected IdentificationType getIdType() {
        return idType;
    }

    protected Integer getId() {
        return id;
    }

    protected Gender getGender() {
        return gender;
    }

    protected String getPhoneNumber() {
        return phoneNumber;
    }

    protected void setIdType(IdentificationType idType) {
        this.idType = idType;
    }

    protected void setId(Integer id) {
        this.id = id;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    protected void setLastName(String lastName) {
        this.lastName = lastName;
    }

    protected void setGender(Gender gender) {
        this.gender = gender;
    }

    protected void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
