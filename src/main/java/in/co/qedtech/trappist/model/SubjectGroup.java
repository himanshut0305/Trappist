package in.co.qedtech.trappist.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "subject_groups", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class SubjectGroup extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NaturalId
    @NotBlank
    @Size(max = 100)
    private String name;

    public SubjectGroup() { }
    public SubjectGroup(@NotBlank @Size(max = 100) String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "SubjectGroup{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
