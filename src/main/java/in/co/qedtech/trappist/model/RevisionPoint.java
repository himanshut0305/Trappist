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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "revision_point")
public class RevisionPoint  extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private int pointIndex;

    @Column(columnDefinition="text")
    private String preface;

    @Column(columnDefinition="text")
    private String explanation;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "revision_slide_id")
    private RevisionSlide revisionSlide;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ContentStatus status;

    public RevisionPoint() { }

    public RevisionPoint(RevisionPoint point, RevisionSlide slide) {
        this.pointIndex = point.getPointIndex();
        this.preface = point.getPreface();
        this.explanation = point.getExplanation();
        this.revisionSlide = slide;
        this.status = ContentStatus.SUBMITTED;
    }

    public RevisionPoint(@NotBlank int pointIndex, String preface, @NotBlank String explanation, @NotBlank RevisionSlide revisionSlide) {
        this.pointIndex = pointIndex;
        this.preface = preface;
        this.explanation = explanation;
        this.revisionSlide = revisionSlide;
        this.status = ContentStatus.SUBMITTED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPointIndex() {
        return pointIndex;
    }

    public void setPointIndex(int pointIndex) {
        this.pointIndex = pointIndex;
    }

    public String getPreface() {
        return preface;
    }

    public void setPreface(String preface) {
        this.preface = preface;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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
        return "RevisionPoint{" +
                "id=" + id +
                ", pointIndex=" + pointIndex +
                ", preface='" + preface + '\'' +
                ", explanation='" + explanation + '\'' +
                ", revisionSlide=" + revisionSlide +
                ", status=" + status +
                '}';
    }
}