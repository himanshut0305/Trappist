package in.co.qedtech.trappist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.ConfirmInteraction;
import in.co.qedtech.trappist.model.RevisionPoint;
import in.co.qedtech.trappist.model.RevisionSlide;

@Repository
public interface RevisionPointRepository extends JpaRepository<RevisionPoint, Integer> {
    List<RevisionPoint> findAll();
    List<RevisionPoint> findByRevisionSlide(RevisionSlide revisionSlide);
}