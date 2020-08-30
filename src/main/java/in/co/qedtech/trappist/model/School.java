package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "schools", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        }),
        @UniqueConstraint(columnNames = {
                "schoolCode"
        })
})
public class School extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String aka;

    @NaturalId
    @NotBlank
    @Size(max = 15)
    private String schoolCode;

    @ManyToOne
    @JoinColumn(name = "board_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Board board;

    private String address;

    @ManyToOne
    @JoinColumn(name = "zip_code_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ZIPCode zipCode;

    @ManyToOne
    @JoinColumn(name = "school_chain_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SchoolChain schoolChain;

    String logoURL;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private SchoolTheme schoolTheme;

    public School() { }
    public School(@NotBlank @Size(max = 100) String name, @NotBlank @Size(max = 15) String aka, @NotBlank @Size(max = 15) String schoolCode, Board board, @NotBlank String address, ZIPCode zipCode, SchoolChain schoolChain, String logoURL, SchoolTheme schoolTheme) {
        this.name = name;
        this.aka = aka;
        this.schoolCode = schoolCode;
        this.board = board;
        this.address = address;
        this.zipCode = zipCode;
        this.schoolChain = schoolChain;
        this.logoURL = logoURL;
        this.schoolTheme = schoolTheme;
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

    public String getAka() {
        return aka;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ZIPCode getZipCode() {
        return zipCode;
    }

    public void setZipCode(ZIPCode zipCode) {
        this.zipCode = zipCode;
    }

    public SchoolChain getSchoolChain() {
        return schoolChain;
    }

    public void setSchoolChain(SchoolChain schoolChain) {
        this.schoolChain = schoolChain;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public SchoolTheme getSchoolTheme() {
        return schoolTheme;
    }

    public void setSchoolTheme(SchoolTheme schoolTheme) {
        this.schoolTheme = schoolTheme;
    }

    @Override
    public String toString() {
        return "School{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", aka='" + aka + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                ", board=" + board +
                ", address='" + address + '\'' +
                ", zipCode=" + zipCode +
                ", schoolChain=" + schoolChain +
                ", logoURL='" + logoURL + '\'' +
                ", schoolTheme=" + schoolTheme +
                '}';
    }
}
