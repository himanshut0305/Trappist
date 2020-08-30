package in.co.qedtech.trappist.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.Chapter;
import in.co.qedtech.trappist.model.Subject;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    List<Chapter> findAll();
    List<Chapter> findBySubject(Subject subject);

    Boolean existsByName(String name);
    Boolean existsByNameAndSubject(String name, Subject subject);
}

