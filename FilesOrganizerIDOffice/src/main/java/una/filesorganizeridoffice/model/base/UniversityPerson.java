package una.filesorganizeridoffice.model.base;

import una.filesorganizeridoffice.model.base.PersonalData;

import java.io.Serializable;
import java.util.Date;

public abstract class UniversityPerson extends PersonalData {
    protected String id_una;
    protected String email;
    protected int office;
    protected int grossSalary;
    protected int netSalary;
    protected Date hireDate;
    protected int licence;

    protected String getId_una() {
        return id_una;
    }

    protected String getEmail() {
        return email;
    }

    protected Integer getOffice() {
        return office;
    }

    protected Integer getGrossSalary() {
        return grossSalary;
    }

    protected Integer getNetSalary() {
        return netSalary;
    }

    protected Date getHireDate() {
        return hireDate;
    }

    protected Integer getLicence() {
        return licence;
    }

    protected void setId_una(String id_una) {
        this.id_una = id_una;
    }

    protected void setEmail(String email) {
        this.email = email;
    }

    protected void setOffice(Integer office) {
        this.office = office;
    }

    protected void setGrossSalary(Integer grossSalary) {
        this.grossSalary = grossSalary;
    }

    public void setNetSalary(Integer netSalary) {
        this.netSalary = netSalary;
    }

    protected void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    protected void setLicence(Integer licence) {
        this.licence = licence;
    }
}