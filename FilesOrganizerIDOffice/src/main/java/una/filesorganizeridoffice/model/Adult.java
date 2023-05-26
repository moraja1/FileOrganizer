package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;
import una.filesorganizeridoffice.model.base.UniversityPerson;

@XLSerializable
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

