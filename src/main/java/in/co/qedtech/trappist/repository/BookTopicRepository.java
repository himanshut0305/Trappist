package in.co.qedtech.trappist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.BookChapter;
import in.co.qedtech.trappist.model.BookTopic;

@Repository
public interface BookTopicRepository extends JpaRepository<BookTopic, Integer> {
    List<BookTopic> findAll();
    List<BookTopic> findByBookChapter(BookChapter bookChapter);

    BookTopic findByNameAndBookChapter(String name, BookChapter bookChapter);
    Boolean existsByNameAndBookChapter(String name, BookChapter bookChapter);
}
