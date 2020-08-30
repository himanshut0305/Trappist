package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class Book extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "subject_year_id")
    private SubjectYear subjectYear;

    private int version;

    public Book() { }

    public Book(@NotBlank String name, SubjectYear subjectYear) {
        this.name = name;
        this.subjectYear = subjectYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SubjectYear getSubjectYear() {
        return subjectYear;
    }

    public void setSubjectYear(SubjectYear subjectYear) {
        this.subjectYear = subjectYear;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void incrementVersion() {
        ++this.version;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", subjectYear=" + subjectYear +
                ", version=" + version +
                '}';
    }
}