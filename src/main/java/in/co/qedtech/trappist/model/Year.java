package in.co.qedtech.trappist.model;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "years")
public class Year extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int name;

    public Year() {}
    public Year(int name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Year{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
