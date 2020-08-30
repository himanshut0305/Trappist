package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "teacher_details")
public class TeacherDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phoneNo;
    private String emailId;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private School school;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teacher_class_subject",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "school_class_subject_id"))
    private Set<SchoolClassSubject> schoolClassSubjects = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    public TeacherDetails() { }
    public TeacherDetails(String phoneNo, String emailId, School school, User user) {
        this.phoneNo = phoneNo;
        this.emailId = emailId;
        this.school = school;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<SchoolClassSubject> getSchoolClassSubjects() {
        return schoolClassSubjects;
    }

    public void setSchoolClassSubjects(Set<SchoolClassSubject> schoolClassSubjects) {
        this.schoolClassSubjects = schoolClassSubjects;
    }

    @Override
    public String toString() {
        return "TeacherDetails{" +
                "id=" + id +
                ", phoneNo='" + phoneNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", school=" + school +
                ", schoolClassSubjects=" + schoolClassSubjects +
                ", user=" + user +
                '}';
    }
}