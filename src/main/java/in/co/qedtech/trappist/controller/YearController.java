package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.SchoolYear;
import in.co.qedtech.trappist.model.Year;
import in.co.qedtech.trappist.repository.SchoolYearRepository;
import in.co.qedtech.trappist.repository.YearRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class YearController {
    private static final Logger logger = LoggerFactory.getLogger(in.co.qedtech.trappist.controller.YearController.class);

    @Autowired YearRepository yearRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/schoolYears")
    public List<Year> getSubjectGroups() {
        List<Year> years = yearRepository.findAll();
        return years;
    }
}

