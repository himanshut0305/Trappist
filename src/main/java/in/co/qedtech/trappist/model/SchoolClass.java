package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "school_classes", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name", "school_year_id"
        })
})
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    private char section;

    @ManyToOne
    @JoinColumn(name = "school_year_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SchoolYear schoolYear;

    public SchoolClass() { }
    public SchoolClass(@NotBlank SchoolYear schoolYear, @NotBlank char section) {
        this.section = section;
        this.schoolYear = schoolYear;

        this.name = schoolYear.getYear().getName() + "" + section;
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

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }
}