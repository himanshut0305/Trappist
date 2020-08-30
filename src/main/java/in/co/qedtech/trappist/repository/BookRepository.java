package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.Book;
import in.co.qedtech.trappist.model.Subject;
import in.co.qedtech.trappist.model.SubjectYear;
import in.co.qedtech.trappist.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAll();
    Book findByName(String name);
    List<Book> findBySubjectYear(SubjectYear subjectYear);

    Boolean existsByName(String name);
}
