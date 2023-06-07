package una.filesorganizeridoffice.model.base;

import una.filesorganizeridoffice.business.api.xl.annotations.XLCellColumn;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellGetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLCellSetValue;
import una.filesorganizeridoffice.business.api.xl.annotations.XLSerializable;
import una.filesorganizeridoffice.business.util.Processes;

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
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "F"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "C"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "F")
    })
    public String getId_una() {
        return id_una;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "K"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "J"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "L")
    })
    public String getEmail() {
        return email;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "M"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "T"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "N")
    })
    public String getOffice() {
        return office;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "N"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "U")
    })
    public Float getGrossSalary() {
        return grossSalary;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "O"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "V")
    })
    public Float getNetSalary() {
        return netSalary;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "P"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "W"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "P")
    })
    public LocalDate getHireDate() {
        return hireDate;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.ADULT_STUDENT, column = "Q"),
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "X"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "Q")
    })
    public Integer getLicence() {
        return licence;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "O")
    })
    public Float getAverageIncome(){
        return averageIncome;
    }
    @XLCellGetValue({
            @XLCellColumn(processOf = Processes.UNDER_AGE_STUDENT, column = "H"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "I")
    })
    public LocalDate getBirthDate() {
        return birthDate;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "H"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "H")
    })
    public void setId_una(Object id_una) {
        if(id_una instanceof String){
            this.id_una = (String)id_una;
        } else {
            this.id_una = String.valueOf(id_una);
        }
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "B"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "B")
    })
    public void setEmail(String email) {
        this.email = email;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "N"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "O")
    })
    public void setOffice(String office) {
        this.office = office;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "O")
    })
    public void setGrossSalary(Object grossSalary) {
        if(grossSalary instanceof String){
            this.grossSalary = Float.valueOf((String) grossSalary);
        }
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "P")
    })
    public void setNetSalary(Object netSalary) {
        if(netSalary instanceof String){
            this.netSalary = Float.valueOf((String) netSalary);
        }
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "Q"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "P")
    })
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.LICENCE, column = "B"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "H")
    })
    public void setLicence(Integer licence) {
        this.licence = licence;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "Q")
    })
    public void setAverageIncome(Float averageIncome) {
        this.averageIncome = averageIncome;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = Processes.STUDENT, column = "U"),
            @XLCellColumn(processOf = Processes.EMPLOYEE, column = "K")
    })
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}