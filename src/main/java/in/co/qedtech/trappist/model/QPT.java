package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.co.qedtech.trappist.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "qpts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name", "book_chapter_id"
        })
})
public class QPT extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int qptIndex;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_chapter_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private BookChapter bookChapter;

    public QPT() { }
    public QPT(int qptIndex, @NotBlank @Size(max = 40) String name, @NotNull BookChapter bookChapter) {
        this.qptIndex = qptIndex;
        this.name = name;
        this.bookChapter = bookChapter;
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

    public int getQptIndex() {
        return qptIndex;
    }

    public void setQptIndex(int qptIndex) {
        this.qptIndex = qptIndex;
    }

    public BookChapter getBookChapter() {
        return bookChapter;
    }

    public void setBookChapter(BookChapter bookChapter) {
        this.bookChapter = bookChapter;
    }

    @Override
    public String toString() {
        return "QPT{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bookChapter=" + bookChapter +
                '}';
    }
}
