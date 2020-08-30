package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.Book;
import in.co.qedtech.trappist.model.BookChapter;
import in.co.qedtech.trappist.model.QPT;
import in.co.qedtech.trappist.model.SubjectYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QPTRepository extends JpaRepository<QPT, Integer> {
    QPT findByNameAndBookChapter(String name, BookChapter bookChapter);
}
