package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.SchoolChain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolChainRepository extends JpaRepository<SchoolChain, Integer> {
    List<SchoolChain> findAll();
    Boolean existsByName(String name);
    Boolean existsByAka(String aka);
}
