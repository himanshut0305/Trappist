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
import javax.validation.constraints.NotNull;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "diagram")
public class Diagram  extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String url;

    @Column(columnDefinition="text")
    private String caption;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "revision_slide_id")
    private RevisionSlide revisionSlide;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ContentStatus status;

    public Diagram() {}
    public Diagram(Diagram diagram, RevisionSlide revisionSlide) {
        this.url = diagram.getUrl();
        this.caption = diagram.getCaption();
        this.revisionSlide = revisionSlide;
        this.status = ContentStatus.SUBMITTED;
    }

    public Diagram(String url, String caption, RevisionSlide revisionSlide) {
        this.url = url;
        this.caption = caption;
        this.revisionSlide = revisionSlide;
        this.status = ContentStatus.SUBMITTED;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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
        return "DIG{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", caption='" + caption + '\'' +
                ", revisionSlide=" + revisionSlide +
                ", status=" + status +
                '}';
    }
}
