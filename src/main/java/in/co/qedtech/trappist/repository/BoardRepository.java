package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findAll();
    Boolean existsByName(String name);
    Boolean existsByAka(String aka);
}
