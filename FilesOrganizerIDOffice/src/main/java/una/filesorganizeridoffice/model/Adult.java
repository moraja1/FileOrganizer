package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.business.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.xl.annotations.XLSubSerializable;
import una.filesorganizeridoffice.model.base.UniversityPerson;

@XLSubSerializable(processOf = "student")
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
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "K")
    })
    public void setCountryResidence(String countryResidence) {
        this.countryResidence = countryResidence;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "L")
    })
    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "M")
    })
    public void setAddress(String address) {
        this.address = address;
    }
}

