package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassesRepository extends JpaRepository<SchoolClass, Integer> {
    List<SchoolClass> findAll();
    List<SchoolClass> findBySchoolYear(SchoolYear schoolYear);
    List<SchoolClass> findBySchoolYearIn(List<SchoolYear> schoolYears);

    Boolean existsByName(String name);
}
