package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.School;
import in.co.qedtech.trappist.model.SchoolYear;
import in.co.qedtech.trappist.payload.GetSchoolYearRequest;
import in.co.qedtech.trappist.repository.SchoolRepository;
import in.co.qedtech.trappist.repository.SchoolYearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
class SchoolYearController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolYearController.class);

    @Autowired SchoolYearRepository schoolYearRepository;
    @Autowired SchoolRepository schoolRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/get/schoolYearsBySchool")
    public List<SchoolYear> getSubjectGroups(@Valid @RequestBody GetSchoolYearRequest request) {
        School school = schoolRepository.getOne(request.getSchoolId());
        List<SchoolYear> schoolYears = schoolYearRepository.findBySchool(school);
        return schoolYears;
    }
}