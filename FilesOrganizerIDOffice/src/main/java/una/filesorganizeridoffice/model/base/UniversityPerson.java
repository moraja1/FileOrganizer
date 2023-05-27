package una.filesorganizeridoffice.model.base;

import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;

import java.time.LocalDate;

@XLSerializable
public abstract class UniversityPerson extends PersonalData {
    protected String id_una;
    protected String email;
    protected String office;
    protected Float grossSalary;
    protected Float netSalary;
    protected LocalDate hireDate;
    protected Integer licence;
    protected Float averageIncome;
    private LocalDate birthDate;
    public String getId_una() {
        return id_una;
    }
    public String getEmail() {
        return email;
    }
    public String getOffice() {
        return office;
    }
    public Float getGrossSalary() {
        return grossSalary;
    }
    public Float getNetSalary() {
        return netSalary;
    }
    public LocalDate getHireDate() {
        return hireDate;
    }
    public Integer getLicence() {
        return licence;
    }
    public Float getAverageIncome() {
        return averageIncome;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "H"),
            @XLCellColumn(processOf = "employee", column = "H")
    })
    public void setId_una(Object id_una) {
        if(id_una instanceof String){
            this.id_una = (String)id_una;
        } else {
            this.id_una = String.valueOf(id_una);
        }
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "B"),
            @XLCellColumn(processOf = "employee", column = "B")
    })
    public void setEmail(String email) {
        this.email = email;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "N"),
            @XLCellColumn(processOf = "employee", column = "O")
    })
    public void setOffice(String office) {
        this.office = office;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "O")
    })
    public void setGrossSalary(Object grossSalary) {
        if(grossSalary instanceof String){
            this.grossSalary = Float.valueOf((String) grossSalary);
        }
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "P")
    })
    public void setNetSalary(Object netSalary) {
        if(netSalary instanceof String){
            this.netSalary = Float.valueOf((String) netSalary);
        }
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "Q"),
            @XLCellColumn(processOf = "employee", column = "P")
    })
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "licence", column = "B"),
            @XLCellColumn(processOf = "employee", column = "H")
    })
    public void setLicence(Integer licence) {
        this.licence = licence;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "employee", column = "Q")
    })
    public void setAverageIncome(Float averageIncome) {
        this.averageIncome = averageIncome;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "U"),
            @XLCellColumn(processOf = "employee", column = "K")
    })
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}