package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
import javax.validation.constraints.NotBlank;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "reveal_interaction")
public class RevealInteraction  extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(columnDefinition="text")
    private String question;

    @NotBlank
    @Column(columnDefinition="text")
    private String answer;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "revision_slide_id")
    private RevisionSlide revisionSlide;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ContentStatus status;

    public RevealInteraction() {}

    public RevealInteraction(RevealInteraction ri, RevisionSlide slide) {
        this.question = ri.getQuestion();
        this.answer = ri.getAnswer();
        this.revisionSlide = slide;
        this.status = ContentStatus.SUBMITTED;
    }

    public RevealInteraction(String question, @NotBlank String answer, @NotBlank RevisionSlide revisionSlide) {
        this.question = question;
        this.answer = answer;
        this.revisionSlide = revisionSlide;
        this.status = ContentStatus.SUBMITTED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public RevisionSlide getRevisionSlide() {
        return revisionSlide;
    }

    public void setRevisionSlide(RevisionSlide revisionSlide) {
        this.revisionSlide = revisionSlide;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RevealInteraction{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", revisionSlide=" + revisionSlide +
                ", status=" + status +
                '}';
    }
}
