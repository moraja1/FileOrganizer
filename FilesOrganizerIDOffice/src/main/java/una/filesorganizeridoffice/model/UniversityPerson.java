package una.filesorganizeridoffice.model;

import java.util.Date;

public class UniversityPerson extends PersonalData {
    private String UNA_ID;
    private String email;
    private Integer office;
    private Integer grossSalary;
    private Integer netSalary;
    private Date hireDate;
    private Integer licence;

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

    protected UniversityPerson(PersonalData p, UniversityPerson uP) {
        super(p);
        this.UNA_ID = uP.getUNA_ID();
        this.email = uP.getEmail();
        this.office = uP.getOffice();
        this.grossSalary = uP.getGrossSalary();
        this.netSalary = uP.getNetSalary();
        this.hireDate = uP.getHireDate();
        this.licence = uP.getLicence();
    }

    protected String getUNA_ID() {
        return UNA_ID;
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
}