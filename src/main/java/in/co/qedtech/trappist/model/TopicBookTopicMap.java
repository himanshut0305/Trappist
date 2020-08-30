package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "topic_book_topic_map", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "topic_id", "book_topic_id"
        })
})
public class TopicBookTopicMap extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "book_topic_id")
    private BookTopic bookTopic;

    public TopicBookTopicMap() { }
    public TopicBookTopicMap(Topic topic, BookTopic bookTopic) {
        this.topic = topic;
        this.bookTopic = bookTopic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public BookTopic getBookTopic() {
        return bookTopic;
    }

    public void setBookTopic(BookTopic bookTopic) {
        this.bookTopic = bookTopic;
    }

    @Override
    public String toString() {
        return "TopicBookTopicMap{" +
                "id=" + id +
                ", topic=" + topic +
                ", bookTopic=" + bookTopic +
                '}';
    }
}
