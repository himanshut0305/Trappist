package in.co.qedtech.trappist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.Book;
import in.co.qedtech.trappist.model.BookTopic;
import in.co.qedtech.trappist.model.RevisionSlide;
import in.co.qedtech.trappist.model.Topic;

@Repository
public interface SlideRepository extends JpaRepository<RevisionSlide, Integer> {
    List<RevisionSlide> findAll();
    List<RevisionSlide> findByBookTopic(BookTopic bookTopic);
    RevisionSlide findByBookTopicAndSlideIndex(BookTopic bookTopic, int slideIndex);
    long countByBookTopic(BookTopic bookTopic);
}