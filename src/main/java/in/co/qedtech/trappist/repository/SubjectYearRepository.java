package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.Subject;
import in.co.qedtech.trappist.model.SubjectYear;
import in.co.qedtech.trappist.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectYearRepository extends JpaRepository<SubjectYear, Integer> {
    List<SubjectYear> findAll();
    List<SubjectYear> findBySubject(Subject subject);
    List<SubjectYear> findByYear(Year year);
    SubjectYear findBySubjectAndYear(Subject subject, Year year);
}
