package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.BookTopic;
import in.co.qedtech.trappist.model.TopicBookTopicMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicMapRepository extends JpaRepository<TopicBookTopicMap, Integer> {
    List<TopicBookTopicMap> findByBookTopic(BookTopic bookTopic);
}
