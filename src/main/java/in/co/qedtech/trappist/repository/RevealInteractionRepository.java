package in.co.qedtech.trappist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.RevealInteraction;
import in.co.qedtech.trappist.model.RevisionSlide;

@Repository
public interface RevealInteractionRepository extends JpaRepository<RevealInteraction, Integer> {
    List<RevealInteraction> findAll();
    List<RevealInteraction> findByRevisionSlide(RevisionSlide revisionSlide);
}