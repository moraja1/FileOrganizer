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
    @XLCellSetValue({
            @XLCellColumn(processOf = "student", column = "H")
    })
    public void setId_una(Object id_una) {
        if(id_una instanceof String){
            this.id_una = (String)id_una;
        } else {
            this.id_una = String.valueOf(id_una);
        }
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
            @XLCellColumn(processOf = "student", column = "Q")
    })
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    @XLCellSetValue({
            @XLCellColumn(processOf = "licence", column = "B")
    })
    public void setLicence(Integer licence) {
        this.licence = licence;
    }

    @Override
    public String toString() {
        return "UniversityPerson{" +
                "id_una='" + id_una + '\'' +
                ", email='" + email + '\'' +
                ", office='" + office + '\'' +
                ", grossSalary=" + grossSalary +
                ", netSalary=" + netSalary +
                ", hireDate=" + hireDate +
                ", licence=" + licence +
                '}';
    }
}