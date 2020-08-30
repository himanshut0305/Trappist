package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "topic_reactions")
public class TopicReaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "book_topic_id")
    private BookTopic bookTopic;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "student_id")
    private User student;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private Reactions reactions;

    public TopicReaction() { }
    public TopicReaction(BookTopic bookTopic, User student, Reactions reactions) {
        this.bookTopic = bookTopic;
        this.student = student;
        this.reactions = reactions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookTopic getBookTopic() {
        return bookTopic;
    }

    public void setBookTopic(BookTopic bookTopic) {
        this.bookTopic = bookTopic;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Reactions getReactions() {
        return reactions;
    }

    public void setReactions(Reactions reactions) {
        this.reactions = reactions;
    }

    @Override
    public String toString() {
        return "TopicReaction{" +
                "id=" + id +
                ", bookTopic=" + bookTopic +
                ", student=" + student +
                ", reactions=" + reactions +
                '}';
    }
}