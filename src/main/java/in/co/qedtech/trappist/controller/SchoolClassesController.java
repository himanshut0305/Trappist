package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.*;
import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.payload.GetSchoolClassesRequest;
import in.co.qedtech.trappist.payload.GetSubjectsBySchoolClassRequest;
import in.co.qedtech.trappist.payload.SaveSchoolClassRequest;
import in.co.qedtech.trappist.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class SchoolClassesController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolClassesController.class);

    @Autowired SchoolClassesRepository schoolClassesRepository;
    @Autowired YearRepository yearRepository;
    @Autowired SchoolYearRepository schoolYearRepository;

    @Autowired SchoolRepository schoolRepository;
    @Autowired SubjectYearRepository subjectYearRepository;
    @Autowired SchoolClassSubjectRepository schoolClassSubjectRepository;

    @Autowired SchoolYearSubjectBookRepository schoolYearSubjectBookRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/get/schoolClassesBySchool")
    public List<SchoolClass> getSchoolClassesBySchool(@Valid @RequestBody GetSchoolClassesRequest request) {
        School school = schoolRepository.getOne(request.getSchoolId());
        List<SchoolYear> schoolYears = schoolYearRepository.findBySchool(school);
        List<SchoolClass> schoolClasses = schoolClassesRepository.findBySchoolYearIn(schoolYears);
        return schoolClasses;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/save/schoolClasses")
    public ResponseEntity<?> saveSchoolClass(@Valid @RequestBody SaveSchoolClassRequest request) {
        String fromSection = request.getFromSection().toUpperCase();
        String toSection = request.getToSection().toUpperCase();

        if(fromSection.length() > 1 || toSection.length() > 1) {
            return new ResponseEntity(new ApiResponse(false, "Section has to be a single letter"), HttpStatus.BAD_REQUEST);
        }
        else {
            if(fromSection.charAt(0) < 'A' || fromSection.charAt(0) > 'Z' || toSection.charAt(0) < 'A' || toSection.charAt(0) > 'Z') {
                return new ResponseEntity(new ApiResponse(false, "Invalid value for Section"), HttpStatus.BAD_REQUEST);
            }
            else {
                if(fromSection.charAt(0) > toSection.charAt(0)) {
                    String temp = fromSection;
                    fromSection = toSection;
                    toSection = temp;
                }

                School school = schoolRepository.getOne(request.getSchoolId());
                Year year = yearRepository.getOne(request.getYearId());
                ArrayList<SubjectYear> subjectYears = (ArrayList<SubjectYear>) subjectYearRepository.findByYear(year);

                SchoolYear schoolYear = new SchoolYear(year, school);
                SchoolYear savedSchoolYear = schoolYearRepository.save(schoolYear);

                List<Subject> subjects = new ArrayList<>();

                for(SubjectYear subjectYear : subjectYears) {
                    SchoolYearSubjectBook sysb = new SchoolYearSubjectBook();
                    sysb.setSchoolYear(savedSchoolYear);
                    sysb.setSubject(subjectYear.getSubject());

                    schoolYearSubjectBookRepository.save(sysb);

                    subjects.add(subjectYear.getSubject());
                }

                for (char f = fromSection.charAt(0); f <= toSection.charAt(0); f++) {
                    SchoolClass schoolClass = new SchoolClass(savedSchoolYear, f);
                    schoolClassesRepository.save(schoolClass);

                    for(Subject sub : subjects) {
                        SchoolClassSubject schoolClassSubject = new SchoolClassSubject(schoolClass, sub);
                        schoolClassSubjectRepository.save(schoolClassSubject);
                    }
                }
            }
        }

        return new ResponseEntity(new ApiResponse(true, request.toString()), HttpStatus.OK);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT"})
    @PostMapping("/get/subjectsBySchoolClass")
    public List<SchoolClassSubject> getSubjectsAndBookBySchoolClass(@Valid @RequestBody GetSubjectsBySchoolClassRequest request) {
        SchoolClass schoolClass = schoolClassesRepository.getOne(request.getSchoolClassId());
        return schoolClassSubjectRepository.findBySchoolClass(schoolClass);
    }

}