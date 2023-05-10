package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.model.base.PersonalData;

import java.io.Serializable;

public class AdultStudent extends UniversityPerson implements Serializable {
    private String countryResidence;
    private String occupancy;
    private String address;

    public AdultStudent() {
    }

    public AdultStudent(PersonalData p, UniversityPerson uP, String countryResidence, String occupancy, String address) {
        super(p, uP);
        this.countryResidence = countryResidence;
        this.occupancy = occupancy;
        this.address = address;
    }

    public String getCountryResidence() {
        return countryResidence;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public String getAddress() {
        return address;
    }
}

