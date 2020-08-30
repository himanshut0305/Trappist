package in.co.qedtech.trappist.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.NaturalId;

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

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "confirm_interaction")
public class ConfirmInteraction extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String question;

    @NotBlank
    private String affirmativeAnswer;

    @NotBlank
    private String negativeAnswer;

    @NotNull
    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "revision_slide_id")
    private RevisionSlide revisionSlide;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ContentStatus status;

    public ConfirmInteraction() {}

    public ConfirmInteraction(ConfirmInteraction ci, RevisionSlide revisionSlide) {
        this.question = ci.getQuestion();
        this.affirmativeAnswer = ci.getAffirmativeAnswer();
        this.negativeAnswer = ci.getNegativeAnswer();
        this.revisionSlide = revisionSlide;
        this.status = ContentStatus.SUBMITTED;
    }

    public ConfirmInteraction(@NotBlank String question, @NotBlank String affirmativeAnswer, @NotBlank String negativeAnswer, @NotBlank RevisionSlide revisionSlide) {
        this.question = question;
        this.affirmativeAnswer = affirmativeAnswer;
        this.negativeAnswer = negativeAnswer;
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

    public String getAffirmativeAnswer() {
        return affirmativeAnswer;
    }

    public void setAffirmativeAnswer(String affirmativeAnswer) {
        this.affirmativeAnswer = affirmativeAnswer;
    }

    public String getNegativeAnswer() {
        return negativeAnswer;
    }

    public void setNegativeAnswer(String negativeAnswer) {
        this.negativeAnswer = negativeAnswer;
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
        return "ConfirmInteraction{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", affirmativeAnswer='" + affirmativeAnswer + '\'' +
                ", negativeAnswer='" + negativeAnswer + '\'' +
                ", revisionSlide=" + revisionSlide +
                ", status=" + status +
                '}';
    }
}