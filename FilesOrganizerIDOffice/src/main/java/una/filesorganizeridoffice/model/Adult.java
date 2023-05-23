package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.model.base.UniversityPerson;

import java.io.Serializable;

public class Adult extends UniversityPerson {
    private String countryResidence;
    private String occupancy;
    private String address;

    public Adult() {
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

    public void setCountryResidence(String countryResidence) {
        this.countryResidence = countryResidence;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

