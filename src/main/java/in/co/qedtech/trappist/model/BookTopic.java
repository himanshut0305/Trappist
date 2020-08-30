package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "book_topics")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BookTopic extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String name;

    private int topicIndex;

    @Column(columnDefinition="text")
    private String description;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "book_chapter_id")
    private BookChapter bookChapter;

    boolean isMapped;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "qpt_id")
    private QPT qpt;

    private boolean doesPrecedeQPT;

    private int version;

    public BookTopic() { }
    public BookTopic(@NotBlank String name, int topicIndex, @NotNull BookChapter bookChapter, String description) {
        this.name = name;
        this.topicIndex = topicIndex;
        this.bookChapter = bookChapter;
        this.description = description;
        this.isMapped = false;
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

    public int getTopicIndex() {
        return topicIndex;
    }

    public void setTopicIndex(int topicIndex) {
        this.topicIndex = topicIndex;
    }

    public BookChapter getBookChapter() {
        return bookChapter;
    }

    public void setBookChapter(BookChapter bookChapter) {
        this.bookChapter = bookChapter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isMapped() {
        return isMapped;
    }

    public void setMapped(boolean mapped) {
        isMapped = mapped;
    }

    public QPT getQpt() {
        return qpt;
    }

    public void setQpt(QPT qpt) {
        this.qpt = qpt;
    }

    public boolean isDoesPrecedeQPT() {
        return doesPrecedeQPT;
    }

    public void setDoesPrecedeQPT(boolean doesPrecedeQPT) {
        this.doesPrecedeQPT = doesPrecedeQPT;
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
        return "BookTopic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topicIndex=" + topicIndex +
                ", description='" + description + '\'' +
                ", bookChapter=" + bookChapter +
                ", isMapped=" + isMapped +
                ", qpt=" + qpt +
                ", doesPrecedeQPT=" + doesPrecedeQPT +
                ", version=" + version +
                '}';
    }
}