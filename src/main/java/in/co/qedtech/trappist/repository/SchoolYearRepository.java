package in.co.qedtech.trappist.repository;

import in.co.qedtech.trappist.model.School;
import in.co.qedtech.trappist.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import in.co.qedtech.trappist.model.SchoolYear;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, Integer> {
    List<SchoolYear> findAll();
    List<SchoolYear> findBySchool(School school);
    SchoolYear findBySchoolAndYear(School school, Year year);
}
