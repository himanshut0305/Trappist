package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.Board;
import in.co.qedtech.trappist.model.SchoolChain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import in.co.qedtech.trappist.model.School;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Integer> {
    List<School> findAll();
    List<School> findByBoard(Board board);
    List<School> findByBoardAndSchoolChain(Board board , SchoolChain schoolChain);

    School findBySchoolCode(String schoolCode);

    Boolean existsByName(String name);
    Boolean existsByAka(String aka);
    Boolean existsBySchoolCode(String code);
}
