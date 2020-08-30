package in.co.qedtech.trappist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.Subject;
import in.co.qedtech.trappist.model.SubjectGroup;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
    List<Subject> findAll();
    List<Subject> findBySubjectGroup(SubjectGroup subjectGroup);

    Subject findByName(String name);
    Boolean existsByName(String name);
}
