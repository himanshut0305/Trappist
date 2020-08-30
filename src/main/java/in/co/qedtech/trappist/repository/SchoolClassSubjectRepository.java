package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.SchoolClass;
import in.co.qedtech.trappist.model.SchoolClassSubject;
import in.co.qedtech.trappist.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassSubjectRepository extends JpaRepository<SchoolClassSubject, Integer> {
    List<SchoolClassSubject> findAll();
    List<SchoolClassSubject> findBySchoolClass(SchoolClass schoolClass);
    SchoolClassSubject findBySchoolClassAndSubject(SchoolClass schoolClass, Subject subject);
}
