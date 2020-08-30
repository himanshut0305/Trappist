package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.Chapter;
import in.co.qedtech.trappist.model.Topic;
import in.co.qedtech.trappist.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YearRepository extends JpaRepository<Year, Integer> {
    List<Year> findAll();
    Year findByName(int name);
}
