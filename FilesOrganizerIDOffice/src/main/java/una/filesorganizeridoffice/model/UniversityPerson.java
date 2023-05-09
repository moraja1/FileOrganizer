package una.filesorganizeridoffice.model;

import java.io.Serializable;
import java.util.Date;

public class UniversityPerson extends PersonalData implements Serializable {
    private String UNA_ID;
    private String email;
    private Integer office;
    private Integer grossSalary;
    private Integer netSalary;
    private Date hireDate;
    private Integer licence;

    public UniversityPerson() {
    }

    public UniversityPerson(String UNA_ID, String email, Integer office, Integer grossSalary, Integer netSalary, Date hireDate, Integer licence) {
        this.UNA_ID = UNA_ID;
        this.email = email;
        this.office = office;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.hireDate = hireDate;
        this.licence = licence;
    }

    public UniversityPerson(PersonalData p, String UNA_ID, String email, Integer office, Integer grossSalary, Integer netSalary, Date hireDate, Integer licence) {
        super(p);
        this.UNA_ID = UNA_ID;
        this.email = email;
        this.office = office;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.hireDate = hireDate;
        this.licence = licence;
    }

    public UniversityPerson(PersonalData p, UniversityPerson uP) {
        super(p);
        this.UNA_ID = uP.getUNA_ID();
        this.email = uP.getEmail();
        this.office = uP.getOffice();
        this.grossSalary = uP.getGrossSalary();
        this.netSalary = uP.getNetSalary();
        this.hireDate = uP.getHireDate();
        this.licence = uP.getLicence();
    }

    public String getUNA_ID() {
        return UNA_ID;
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

    public Date getHireDate() {
        return hireDate;
    }

    public Integer getLicence() {
        return licence;
    }

    public void setUNA_ID(String UNA_ID) {
        this.UNA_ID = UNA_ID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOffice(Integer office) {
        this.office = office;
    }

    public void setGrossSalary(Integer grossSalary) {
        this.grossSalary = grossSalary;
    }

    public void setNetSalary(Integer netSalary) {
        this.netSalary = netSalary;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public void setLicence(Integer licence) {
        this.licence = licence;
    }
}