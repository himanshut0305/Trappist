package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAll();
    List<Question> findByTopic(Topic topic);
    List<Question> findByTopicAndCreatedBy(Topic topic, User createdBy);
    List<Question> findByRevisionSlide(RevisionSlide revisionSlide);
    Question findByRevisionSlideAndStatus(RevisionSlide revisionSlide, ContentStatus status);
}
