package in.co.qedtech.trappist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.Chapter;
import in.co.qedtech.trappist.model.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {
    List<Topic> findAll();
    List<Topic> findByChapter(Chapter chapter);

    Boolean existsByName(String name);
    Boolean existsByNameAndChapter(String name, Chapter chapter);
}