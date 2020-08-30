package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "school_class_subjects", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
            "school_class_id", "subject_id"
        }, name = "UK_SchoolClassId_SubjectId")
})
public class SchoolClassSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "school_class_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SchoolClass schoolClass;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Subject subject;

    public SchoolClassSubject() {}
    public SchoolClassSubject(SchoolClass schoolClass, Subject subject) {
        this.schoolClass = schoolClass;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "SchoolClassSubject{" +
                "id=" + id +
                ", schoolClass=" + schoolClass +
                ", subject=" + subject +
                '}';
    }
}