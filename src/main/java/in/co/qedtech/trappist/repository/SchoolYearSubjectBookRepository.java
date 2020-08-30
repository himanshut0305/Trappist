package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.Book;
import in.co.qedtech.trappist.model.SchoolYear;
import in.co.qedtech.trappist.model.SchoolYearSubjectBook;
import in.co.qedtech.trappist.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolYearSubjectBookRepository extends JpaRepository <SchoolYearSubjectBook, Integer> {
    List<SchoolYearSubjectBook> findAll();

    List<SchoolYearSubjectBook> findBySchoolYear(SchoolYear schoolYear);
    List<SchoolYearSubjectBook> findBySubject(Subject subject);
    List<SchoolYearSubjectBook> findByBook(Book book);

    SchoolYearSubjectBook findBySchoolYearAndSubject(SchoolYear schoolYear, Subject subject);
}
