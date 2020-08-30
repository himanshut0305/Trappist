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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SchoolYearSubjectBookController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolYearSubjectBookController.class);

    @Autowired SchoolYearRepository schoolYearRepository;
    @Autowired SubjectRepository subjectRepository;
    @Autowired BookRepository bookRepository;

    @Autowired SchoolClassesRepository schoolClassesRepository;
    @Autowired SchoolYearSubjectBookRepository schoolYearSubjectBookRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT"})
    @PostMapping("/get/subjectsAndBookBySchoolYear")
    public List<SchoolYearSubjectBook> getSubjectsAndBookBySchoolYear(@Valid @RequestBody GetSubjectBookRequest request) {
        SchoolYear schoolYear = schoolYearRepository.getOne(request.getSchoolYearId());
        List<SchoolYearSubjectBook> schoolYearSubjectBooks = schoolYearSubjectBookRepository.findBySchoolYear(schoolYear);

        return schoolYearSubjectBooks;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/save/bookForSchoolYearAndSubject")
    public ResponseEntity<?> saveBookForSchoolYearAndSubject(@Valid @RequestBody SaveSchoolYearSubjectBookRequest request) {
        SchoolYear schoolYear = schoolYearRepository.getOne(request.getSchoolYearId());
        Subject subject = subjectRepository.getOne(request.getSubjectId());

        logger.error(schoolYear.toString());
        logger.error(subject.toString());

        SchoolYearSubjectBook schoolYearSubjectBook = schoolYearSubjectBookRepository.findBySchoolYearAndSubject(schoolYear, subject);
        Book book = bookRepository.getOne(request.getBookId());
        schoolYearSubjectBook.setBook(book);

        schoolYearSubjectBookRepository.save(schoolYearSubjectBook);

        return new ResponseEntity(new ApiResponse(true, "Book assigned successfully"), HttpStatus.CREATED);
    }
}
