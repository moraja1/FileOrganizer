package una.filesorganizeridoffice.model;

import java.io.Serializable;
import java.util.Date;

public class UniversityPerson extends PersonalData implements Serializable {
    private String id_una;
    private String email;
    private int office;
    private int grossSalary;
    private int netSalary;
    private Date hireDate;
    private int licence;

    public UniversityPerson() {
    }

    public UniversityPerson(String id_una, String email, Integer office, Integer grossSalary, Integer netSalary, Date hireDate, Integer licence) {
        this.id_una = id_una;
        this.email = email;
        this.office = office;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.hireDate = hireDate;
        this.licence = licence;
    }

    public UniversityPerson(PersonalData p, String id_una, String email, Integer office, Integer grossSalary, Integer netSalary, Date hireDate, Integer licence) {
        super(p);
        this.id_una = id_una;
        this.email = email;
        this.office = office;
        this.grossSalary = grossSalary;
        this.netSalary = netSalary;
        this.hireDate = hireDate;
        this.licence = licence;
    }

    public UniversityPerson(PersonalData p, UniversityPerson uP) {
        super(p);
        this.id_una = uP.getId_una();
        this.email = uP.getEmail();
        this.office = uP.getOffice();
        this.grossSalary = uP.getGrossSalary();
        this.netSalary = uP.getNetSalary();
        this.hireDate = uP.getHireDate();
        this.licence = uP.getLicence();
    }

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

    public Date getHireDate() {
        return hireDate;
    }

    public Integer getLicence() {
        return licence;
    }

    public void setId_una(String id_una) {
        this.id_una = id_una;
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