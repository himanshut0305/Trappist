package in.co.qedtech.trappist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.ConfirmInteraction;
import in.co.qedtech.trappist.model.RevisionSlide;

@Repository
public interface ConfirmInteractionRepository extends JpaRepository<ConfirmInteraction, Integer> {
    List<ConfirmInteraction> findAll();
    List<ConfirmInteraction> findByRevisionSlide(RevisionSlide revisionSlide);
}