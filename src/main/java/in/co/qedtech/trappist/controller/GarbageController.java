package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.*;
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

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.payload.GetSubjectsRequest;
import in.co.qedtech.trappist.payload.SaveSubjectRequest;

@RestController
@RequestMapping("/api")
class GarbageController {
    private static final Logger logger = LoggerFactory.getLogger(GarbageController.class);

    @Autowired SubjectGroupRepository subjectGroupRepository;
    @Autowired SubjectRepository subjectRepository;
    @Autowired SchoolYearRepository schoolYearRepository;

    @Autowired SubjectYearRepository subjectYearRepository;
    @Autowired YearRepository yearRepository;
    @Autowired SchoolChainRepository schoolChainRepository;

    @Autowired SchoolYearSubjectBookRepository schoolYearSubjectBookRepository;
    @Autowired BookRepository bookRepository;
    @Autowired BookChapterRepository bookChapterRepository;

    @Autowired BookTopicRepository bookTopicRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/init/testData")
    public Boolean initTestData() {
        logger.info("Initialising test data");
        ArrayList<SubjectGroup> subjectGroups = new ArrayList<>();

        subjectGroups.add(new SubjectGroup("Science"));
        subjectGroups.add(new SubjectGroup("Social Science"));
        subjectGroups.add(new SubjectGroup("Mathematics"));
        subjectGroups.add(new SubjectGroup("English"));

        ArrayList<SubjectGroup> savedSubjectGroups = (ArrayList<SubjectGroup>) subjectGroupRepository.saveAll(subjectGroups);

        ArrayList<Subject> subjects = new ArrayList<>();

        subjects.add(new Subject("Physics", savedSubjectGroups.get(0)));
        subjects.add(new Subject("Chemistry", savedSubjectGroups.get(0)));
        subjects.add(new Subject("Biology", savedSubjectGroups.get(0)));
        subjects.add(new Subject("Environmental Science", savedSubjectGroups.get(0)));

        ArrayList<Subject> savedSubjects = (ArrayList<Subject>) subjectRepository.saveAll(subjects);

        ArrayList<Year> years = new ArrayList<>();

        years.add(new Year(10));
        years.add(new Year(9));
        years.add(new Year(8));

        ArrayList<Year> savedYears = (ArrayList<Year>) yearRepository.saveAll(years);

        ArrayList<SubjectYear> subjectYears = new ArrayList<>();

        subjectYears.add(new SubjectYear(savedSubjects.get(0), savedYears.get(0)));
        subjectYears.add(new SubjectYear(savedSubjects.get(1), savedYears.get(0)));
        subjectYears.add(new SubjectYear(savedSubjects.get(2), savedYears.get(0)));
        subjectYears.add(new SubjectYear(savedSubjects.get(3), savedYears.get(0)));

        subjectYears.add(new SubjectYear(savedSubjects.get(0), savedYears.get(1)));
        subjectYears.add(new SubjectYear(savedSubjects.get(1), savedYears.get(1)));
        subjectYears.add(new SubjectYear(savedSubjects.get(2), savedYears.get(1)));
        subjectYears.add(new SubjectYear(savedSubjects.get(3), savedYears.get(1)));

        ArrayList<SubjectYear> savedSubjectYears = (ArrayList<SubjectYear>) subjectYearRepository.saveAll(subjectYears);

        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book("NCERT Physics, 9th", savedSubjectYears.get(4)));
        books.add(new Book("ABC Physics, 9th", savedSubjectYears.get(4)));
        books.add(new Book("Arihant Physics, 9th", savedSubjectYears.get(4)));

        books.add(new Book("NCERT Chemistry, 9th", savedSubjectYears.get(5)));
        books.add(new Book("ABC Chemistry, 9th", savedSubjectYears.get(5)));
        books.add(new Book("Arihant Chemistry, 9th", savedSubjectYears.get(5)));

        ArrayList<Book> savedBooks = (ArrayList<Book>) bookRepository.saveAll(books);

        return true;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/init/moreTestData")
    public Boolean initMoreTestData() {
        logger.info("Initialising More test data");

        ArrayList<SchoolYearSubjectBook> schoolYearSubjectBooks = (ArrayList<SchoolYearSubjectBook>) schoolYearSubjectBookRepository.findAll();
        ArrayList<SchoolYear> schoolYears = (ArrayList<SchoolYear>) schoolYearRepository.findAll();
        ArrayList<Subject> subjects = (ArrayList<Subject>) subjectRepository.findAll();

        for (SchoolYear schoolYear : schoolYears) {
            for (Subject subject :subjects) {
                SchoolYearSubjectBook schoolYearSubjectBook =new SchoolYearSubjectBook();
                schoolYearSubjectBook.setSchoolYear(schoolYear);
                schoolYearSubjectBook.setSubject(subject);

                schoolYearSubjectBooks.add(schoolYearSubjectBook);
            }
        }

        schoolYearSubjectBookRepository.saveAll(schoolYearSubjectBooks);
        return true;
    }
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/init/testData2")
    public Boolean initTestData2() {
        logger.info("Initialising test data");
        ArrayList<SchoolChain> schoolChains = new ArrayList<>();

        schoolChains.add(new SchoolChain("Delhi Public School", "DPS"));
        schoolChains.add(new SchoolChain("Carmel Convent School","CCS"));
        schoolChains.add(new SchoolChain("Kendriya Vidyalay","KV"));

        ArrayList<SchoolChain> savedSchoolChains = (ArrayList<SchoolChain>) schoolChainRepository.saveAll(schoolChains);

        return true;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/testing/simpleTest")
    public ArrayList<Subject> simpleTest() {
        ArrayList<Subject> subjects = (ArrayList<Subject>) subjectRepository.findAll();
        return subjects;
    }
}