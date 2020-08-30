package in.co.qedtech.trappist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "qpt_results", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "qpt_id", "user_id"
        })
})
public class QPTResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "qpt_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private QPT qpt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    private double score;
    private Date attemptedOn;

    public QPTResult() {
    }

    public QPTResult(QPT qpt, User user, Date attemptedOn) {
        this.qpt = qpt;
        this.user = user;
        this.attemptedOn = attemptedOn;
    }

    public Date getAttemptedOn() {
        return attemptedOn;
    }

    public void setAttemptedOn(Date attemptedOn) {
        this.attemptedOn = attemptedOn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public QPT getQpt() {
        return qpt;
    }

    public void setQpt(QPT qpt) {
        this.qpt = qpt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "QPTResult{" +
                "id=" + id +
                ", qpt=" + qpt +
                ", user=" + user +
                ", score=" + score +
                ", attemptedOn=" + attemptedOn +
                '}';
    }
}