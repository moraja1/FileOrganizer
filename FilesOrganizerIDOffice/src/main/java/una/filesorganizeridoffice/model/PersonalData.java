package una.filesorganizeridoffice.model;

public class PersonalData {
    private IdentificationType idType;
    private Integer id;
    private String name;
    private String middleName;
    private String lastName;
    private Gender gender;
    private Integer phoneNumber;

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

    protected Integer getPhoneNumber() {
        return phoneNumber;
    }

}
