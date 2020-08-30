package in.co.qedtech.trappist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.SubjectGroup;

@Repository
public interface SubjectGroupRepository extends JpaRepository<SubjectGroup, Integer> {
    List<SubjectGroup> findAll();

    Boolean existsByName(String name);
}
