package una.filesorganizeridoffice.model;

import una.filesorganizeridoffice.model.base.UniversityPerson;

import java.time.LocalDate;


public class UnderAgeStudent extends UniversityPerson {
    private Authorized authorized;
    public UnderAgeStudent() {
    }
    public Authorized getAuthorized() {
        return authorized;
    }
    public void setAuthorized(Authorized authorized) {
        this.authorized = authorized;
    }
    @Override
    public String toString() {
        return "UnderAgeStudent{" +
                "authorized=" + authorized +
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
