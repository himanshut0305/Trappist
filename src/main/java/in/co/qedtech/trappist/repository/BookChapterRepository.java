package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.Book;
import in.co.qedtech.trappist.model.BookChapter;

@Repository
public interface BookChapterRepository extends JpaRepository<BookChapter, Integer> {
    List<BookChapter> findAll();
    List<BookChapter> findByBook(Book book);

    BookChapter findByNameAndBook(String name, Book book);

    Boolean existsByNameAndBook(String name, Book book);
}