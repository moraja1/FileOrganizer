package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellGetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;
import una.filesorganizeridoffice.business.util.Processes;
import una.filesorganizeridoffice.model.base.UniversityPerson;

@XLSerializable
public class Adult extends UniversityPerson {
    private String countryResidence;
    private String occupancy;
    private String address;
    private String hasAccount;

    public Adult() {
    }

    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "I"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "J")
    })
    public String getCountryResidence() {
        return countryResidence;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "J"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "K")
    })
    public String getOccupancy() {
        return occupancy;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "L"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "M")
    })
    public String getAddress() {
        return address;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "R")
    })
    public String getHasAccount() {
        return hasAccount;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "K"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "L")
    })
    public void setCountryResidence(String countryResidence) {
        this.countryResidence = countryResidence;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "L"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "M")
    })
    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "M"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "N")
    })
    public void setAddress(String address) {
        this.address = address;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "R")
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

