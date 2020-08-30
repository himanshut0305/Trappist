package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends JpaRepository<Badge,Integer> {

}
