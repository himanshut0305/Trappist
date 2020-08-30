package in.co.qedtech.trappist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import in.co.qedtech.trappist.model.audit.DateAudit;

@Entity
@Table(name = "revision_slide")
public class RevisionSlide extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private int slideIndex;

    @ManyToOne
    @JoinColumn(name = "book_topic_id")
    private BookTopic bookTopic;

    @NotNull
    private boolean hasInteractiveQuestion;

    @Column(columnDefinition="text")
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private RevisionSlideType type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by_user_id")
    private User updatedBy;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ContentStatus status;

    public RevisionSlide() {  }
    public RevisionSlide(RevisionSlide slide, User user) {
        this.slideIndex = slide.getSlideIndex();
        this.bookTopic = slide.getBookTopic();
        this.createdBy = user;
        this.updatedBy = user;
        this.type = slide.getType();
        this.hasInteractiveQuestion = slide.isHasInteractiveQuestion();
        this.status = slide.getStatus();
        this.comment = slide.getComment();
    }

    public RevisionSlide(@NotBlank int slideIndex, @NotBlank BookTopic bookTopic, @NotNull User user, RevisionSlideType type) {
        this.slideIndex = slideIndex;
        this.bookTopic = bookTopic;
        this.createdBy = user;
        this.updatedBy = user;
        this.type = type;
        this.hasInteractiveQuestion = false;
        this.status = ContentStatus.SUBMITTED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSlideIndex() {
        return slideIndex;
    }

    public void setSlideIndex(int slideIndex) {
        this.slideIndex = slideIndex;
    }

    public BookTopic getBookTopic() {
        return bookTopic;
    }

    public void setBookTopic(BookTopic bookTopic) {
        this.bookTopic = bookTopic;
    }

    public boolean isHasInteractiveQuestion() {
        return hasInteractiveQuestion;
    }

    public void setHasInteractiveQuestion(boolean hasInteractiveQuestion) {
        this.hasInteractiveQuestion = hasInteractiveQuestion;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }

    public RevisionSlideType getType() {
        return type;
    }

    public void setType(RevisionSlideType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "RevisionSlide{" +
                "id=" + id +
                ", slideIndex=" + slideIndex +
                ", bookTopic=" + bookTopic +
                ", hasInteractiveQuestion=" + hasInteractiveQuestion +
                ", comment='" + comment + '\'' +
                ", type=" + type +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                ", status=" + status +
                '}';
    }
}