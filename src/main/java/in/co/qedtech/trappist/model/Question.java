package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "questions")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Question  extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(columnDefinition="text")
    private String question;

    @NotBlank
    private String instruction;

    @Enumerated(EnumType.STRING)
    @Column(length = 60)
    private QuestionType type;

    @ManyToOne
    @JoinColumn(name = "revision_slide_id")
    private RevisionSlide revisionSlide;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "question_options",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<Option> options = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private ContentStatus status;

    @OneToOne
    @JoinColumn(name = "diagram_id")
    private Diagram diagram;

    @Column(columnDefinition="text")
    private String comment;

    private int level;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private QuestionCategory category;

    public Question() {
        level = 3;
        category = QuestionCategory.CONCEPTUAL;
    }

    public Question(Question ques, RevisionSlide revisionSlide, Set<Option> options) {
        this.question = ques.getQuestion();
        this.instruction = ques.getInstruction();
        this.type = ques.getType();
        this.topic = ques.getTopic();
        this.options = options;
        this.status = ContentStatus.SUBMITTED;
        this.revisionSlide = revisionSlide;
        level = 3;
        category = QuestionCategory.CONCEPTUAL;
    }

    public Question(@NotNull String question, @NotNull String instruction, QuestionType type, RevisionSlide revisionSlide, @NotBlank Topic topic, Set<Option> options) {
        this.question = question;
        this.instruction = instruction;
        this.type = type;
        this.revisionSlide = revisionSlide;
        this.topic = topic;
        this.options = options;
        this.status = ContentStatus.SUBMITTED;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public QuestionType getType() {
        return type;
    }

    public String getQuestionType() {
        if (type == QuestionType.MCQ_Single_Answer) {
            return "MCQ_Single_Answer";
        }
        else if (type == QuestionType.MCQ_Multiple_Answer) {
            return "MCQ_Multiple_Answer";
        }
        else if (type == QuestionType.MCQ_True_False) {
            return "MCQ_True_False";
        }
        else if (type == QuestionType.One_Word_Answer) {
            return "MCQ_One_Word_Answer";
        }
        else if (type == QuestionType.Jumbled_Words) {
            return "MCQ_Jumbled_Words";
        }
        else if (type == QuestionType.Match_The_Columns) {
            return "MCQ_Match_The_Columns";
        }
        else {
            return "Undetermined";
        }
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public RevisionSlide getRevisionSlide() {
        return revisionSlide;
    }

    public void setRevisionSlide(RevisionSlide revisionSlide) {
        this.revisionSlide = revisionSlide;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public ContentStatus getStatus() {
        return status;
    }

    public void setStatus(ContentStatus status) {
        this.status = status;
    }

    public Diagram getDiagram() {
        return diagram;
    }

    public void setDiagram(Diagram diagram) {
        this.diagram = diagram;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public QuestionCategory getCategory() {
        return category;
    }

    public void setCategory(QuestionCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", instruction='" + instruction + '\'' +
                ", type=" + type +
                ", revisionSlide=" + revisionSlide +
                ", topic=" + topic +
                ", options=" + options +
                ", status=" + status +
                ", diagram=" + diagram +
                ", comment='" + comment + '\'' +
                ", level=" + level +
                ", category=" + category +
                '}';
    }
}
