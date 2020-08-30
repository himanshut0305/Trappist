package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.SchoolClass;
import in.co.qedtech.trappist.model.StudentDetails;
import in.co.qedtech.trappist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentDetailsRepository extends JpaRepository<StudentDetails, Integer> {
    List<StudentDetails> findAll();

    StudentDetails findByUser(User user);
    List<StudentDetails> findBySchoolClass(SchoolClass schoolClass);
}
