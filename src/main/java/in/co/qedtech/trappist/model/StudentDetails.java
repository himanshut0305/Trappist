package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.co.qedtech.trappist.model.audit.UserDateAudit;

import javax.persistence.*;

@Entity
public class StudentDetails extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    String rollNo;
    String admissionNo;

    @ManyToOne
    @JoinColumn(name = "school_class_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    SchoolClass schoolClass;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    User user;

    public StudentDetails() {}
    public StudentDetails(String rollNo, String admissionNo, SchoolClass schoolClass, User user) {
        this.rollNo = rollNo;
        this.admissionNo = admissionNo;
        this.schoolClass = schoolClass;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "StudentDetail{" +
                "id=" + id +
                ", rollNo='" + rollNo + '\'' +
                ", admissionNo='" + admissionNo + '\'' +
                ", schoolClass=" + schoolClass +
                ", user=" + user +
                '}';
    }
}
