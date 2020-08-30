package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.School;
import in.co.qedtech.trappist.model.TeacherDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherDetailsRepository extends JpaRepository<TeacherDetails, Integer> {
    List<TeacherDetails> findAll();
    List<TeacherDetails> findBySchool(School school);
}
