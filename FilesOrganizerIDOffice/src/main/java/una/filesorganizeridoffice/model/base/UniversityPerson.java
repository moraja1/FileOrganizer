package una.filesorganizeridoffice.model.base;

import una.filesorganizeridoffice.business.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.xl.annotations.XLCellSetValue;

import java.time.LocalDate;

public abstract class UniversityPerson extends PersonalData {
    protected String id_una;
    protected String email;
    protected int office;
    protected int grossSalary;
    protected int netSalary;
    protected LocalDate hireDate;
    protected int licence;
    public String getId_una() {
        return id_una;
    }

    public String getEmail() {
        return email;
    }

    public Integer getOffice() {
        return office;
    }

    public Integer getGrossSalary() {
        return grossSalary;
    }

    public Integer getNetSalary() {
        return netSalary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Integer getLicence() {
        return licence;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "H")
    })
    public void setId_una(String id_una) {
        this.id_una = id_una;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "B")
    })
    public void setEmail(String email) {
        this.email = email;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "N")
    })
    public void setOffice(Integer office) {
        this.office = office;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "O")
    })
    public void setGrossSalary(Integer grossSalary) {
        this.grossSalary = grossSalary;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "P")
    })
    public void setNetSalary(Integer netSalary) {
        this.netSalary = netSalary;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "Q")
    })
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "B")
    })
    public void setLicence(Integer licence) {
        this.licence = licence;
    }
}