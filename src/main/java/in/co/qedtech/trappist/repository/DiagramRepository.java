package in.co.qedtech.trappist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.Diagram;
import in.co.qedtech.trappist.model.RevisionSlide;

@Repository
public interface DiagramRepository extends JpaRepository<Diagram, Integer> {
    List<Diagram> findAll();
    List<Diagram> findByRevisionSlide(RevisionSlide revisionSlide);
}