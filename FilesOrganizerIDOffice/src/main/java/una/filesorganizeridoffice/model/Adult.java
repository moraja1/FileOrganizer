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
    private String hasAccount;
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
    public String getHasAccount() {
        return hasAccount;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "K"),
            @XLCellColumn(processOf = "employee", column = "L")
    })
    public void setCountryResidence(String countryResidence) {
        this.countryResidence = countryResidence;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "L"),
            @XLCellColumn(processOf = "employee", column = "M")
    })
    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "M"),
            @XLCellColumn(processOf = "employee", column = "N")
    })
    public void setAddress(String address) {
        this.address = address;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "employee", column = "R")
    })
    public void setHasAccount(String hasAccount) {
        this.hasAccount = hasAccount;
    }

    @Override
    public String toString() {
        return "Adult{" +
                "countryResidence='" + countryResidence + '\'' +
                ", occupancy='" + occupancy + '\'' +
                ", address='" + address + '\'' +
                ", hasAccount='" + hasAccount + '\'' +
                ", id_una='" + id_una + '\'' +
                ", email='" + email + '\'' +
                ", office='" + office + '\'' +
                ", grossSalary=" + grossSalary +
                ", netSalary=" + netSalary +
                ", hireDate=" + hireDate +
                ", licence=" + licence +
                ", averageIncome=" + averageIncome +
                ", idType=" + idType +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

