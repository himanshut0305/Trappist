package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity

@Table(name = "book_chapters", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name", "book_id"
        })
})
public class BookChapter extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    private int chapterIndex;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "book_id")
    private Book book;

    private int version;

    public BookChapter() { }
    public BookChapter(@NotBlank String name, int chapterIndex, Book book) {
        this.name = name;
        this.chapterIndex = chapterIndex;
        this.book = book;
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

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
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
        return "BookChapter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chapterIndex=" + chapterIndex +
                ", book=" + book +
                ", version=" + version +
                '}';
    }
}
