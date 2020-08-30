package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.*;
import in.co.qedtech.trappist.payload.*;
import in.co.qedtech.trappist.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
class SubjectController {
    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Autowired SchoolYearRepository schoolYearRepository;
    @Autowired SchoolYearSubjectBookRepository schoolYearSubjectBookRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private SubjectGroupRepository subjectGroupRepository;

    @Autowired SchoolClassesRepository schoolClassesRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/subjects")
    public List<Subject> getSubjects() {
        return subjectRepository.findAll();
    }


    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/get/subjectsByGroup")
    public List<Subject> getSubjectsByGroup(@Valid @RequestBody GetSubjectsRequest getSubjectsRequest) {
        Long id = getSubjectsRequest.getSubjectGroupId();
        Integer subjectGroupId = Integer.parseInt(id.toString());
        SubjectGroup subjectGroup = subjectGroupRepository.getOne(subjectGroupId);

        return subjectRepository.findBySubjectGroup(subjectGroup);
    }

    @Secured("ROLE_SUPER_ADMIN")
    @PostMapping(value="/save/subject")
    public ResponseEntity<?> saveSubjectGroup(@Valid @RequestBody SaveSubjectRequest saveSubjectRequest) {
        if(subjectRepository.existsByName(saveSubjectRequest.getName())) {
            return new ResponseEntity(new ApiResponse(false, "Name already exists"), HttpStatus.BAD_REQUEST);
        }

        Long id = saveSubjectRequest.getSubjectGroupId();
        Integer iid = Integer.parseInt(id.toString());

        SubjectGroup subjectGroup = subjectGroupRepository.getOne(iid);
        Subject subject = new Subject(saveSubjectRequest.getName(), subjectGroup);
        subjectRepository.save(subject);

        return new ResponseEntity(new ApiResponse(true, "Subject Group created successfully"), HttpStatus.CREATED);
    }

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping(value="/delete/subject/{subjectId}")
    public ResponseEntity<?> deleteSubject(@PathVariable(value = "subjectId") int subjectId) {
        Subject subject = subjectRepository.getOne(subjectId);
        subjectRepository.delete(subject);

        return new ResponseEntity(new ApiResponse(true, "Subject deleted successfully"), HttpStatus.OK);
    }

    @Secured("ROLE_STUDENT")
    @PostMapping(value = "/get/subjectsForClass")
    public List<SchoolYearSubjectBook> getSubjectsForClass(@Valid @RequestBody GetSubjectsForClassRequest request) {
        SchoolClass schoolClass = schoolClassesRepository.getOne(request.getSchoolClassId());
        SchoolYear schoolYear = schoolClass.getSchoolYear();

        return schoolYearSubjectBookRepository.findBySchoolYear(schoolYear);
    }
}