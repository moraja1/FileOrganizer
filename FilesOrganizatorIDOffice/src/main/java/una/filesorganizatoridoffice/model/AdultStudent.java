package una.filesorganizatoridoffice.model;

public class AdultStudent extends UniversityPerson{
    String countryResidence;
    String occupancy;
    String address;

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

