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
class BookChapterController {
    private static final Logger logger = LoggerFactory.getLogger(BookChapterController.class);

    @Autowired private SubjectRepository subjectRepository;
    @Autowired private SchoolRepository schoolRepository;
    @Autowired private YearRepository yearRepository;

    @Autowired private ChapterRepository chapterRepository;
    @Autowired private SchoolYearRepository schoolYearRepository;
    @Autowired private SchoolYearSubjectBookRepository schoolYearSubjectBookRepository;

    @Autowired private BookRepository bookRepository;
    @Autowired private BookChapterRepository bookChapterRepository;
    @Autowired private BookTopicRepository bookTopicRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/bookChapters")
    public List<BookChapter> getBookChapters() {
        List<BookChapter> bookChapters = bookChapterRepository.findAll();
        return bookChapters;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/bookChaptersByBook/{bookId}")
    public List<BookChapter> getBookChaptersByBook(@PathVariable(value = "bookId") int bookId) {
        Book book = bookRepository.getOne(bookId);
        List<BookChapter> bookChapterList = bookChapterRepository.findByBook(book);
        return bookChapterList;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT"})
    @PostMapping("/get/bookChaptersByBookName")
    public List<BookChapter> getBookChaptersByBook(@Valid @RequestBody GetChaptersByBookNameRequest request) {
        Book book = bookRepository.findByName(request.getBookName());
        List<BookChapter> bookChapterList = bookChapterRepository.findByBook(book);
        return bookChapterList;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT"})
    @PostMapping("/get/bookChaptersBySubjectSchoolYear")
    public List<BookChapter> getBookChaptersBySubjectSchoolYear(@Valid @RequestBody GetBookChaptersBySubjectSchoolYearRequest request) {
        School school = schoolRepository.findBySchoolCode(request.getSchoolCode());
        Subject subject = subjectRepository.findByName(request.getSubjectName());
        Year year = yearRepository.findByName(request.getYear());

        SchoolYear schoolYear = schoolYearRepository.findBySchoolAndYear(school, year);
        SchoolYearSubjectBook sysb = schoolYearSubjectBookRepository.findBySchoolYearAndSubject(schoolYear, subject);
        Book book = sysb.getBook();

        return bookChapterRepository.findByBook(book);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/save/bookChapter")
    public ResponseEntity<?> saveBookChapter(@Valid @RequestBody SaveBookChapterRequest saveBookChapterRequest) {
        Long id = saveBookChapterRequest.getBookId();
        Integer bookId = Integer.parseInt(id.toString());
        Book book = bookRepository.getOne(bookId);

        if(bookChapterRepository.existsByNameAndBook(saveBookChapterRequest.getName(), book)) {
            return new ResponseEntity(new ApiResponse(false, "Chapter already exists"), HttpStatus.BAD_REQUEST);
        }

        BookChapter bookChapter = new BookChapter(saveBookChapterRequest.getName(), saveBookChapterRequest.getChapterIndex(), book);
        bookChapterRepository.save(bookChapter);

        book.incrementVersion();
        bookRepository.save(book);

        return new ResponseEntity(new ApiResponse(true, "Book Chapter created successfully"), HttpStatus.CREATED);
    }

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping(value="/delete/bookChapter/{chapterId}")
    public ResponseEntity<?> deleteBookChapter(@PathVariable(value = "chapterId") int chapterId) {
        BookChapter chapter = bookChapterRepository.getOne(chapterId);
        List<BookTopic> topics = bookTopicRepository.findByBookChapter(chapter);
        if(topics.size() > 0) {
            return new ResponseEntity(new ApiResponse(false, "Chapter has child topics. Cannot delete."), HttpStatus.NOT_IMPLEMENTED);
        }
        else {
            bookChapterRepository.delete(chapter);
            return new ResponseEntity(new ApiResponse(true, "Chapter deleted successfully"), HttpStatus.OK);
        }
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/update/bookChapter")
    public ResponseEntity<?> updateBookChapter(@Valid @RequestBody UpdateBookChapterRequest updateBookChapterRequest) {
        Integer id = updateBookChapterRequest.getBookChapterId();
        BookChapter bookChapter = bookChapterRepository.getOne(id);

        bookChapter.setName(updateBookChapterRequest.getBookChapterName());
        bookChapter.setChapterIndex(updateBookChapterRequest.getBookChapterIndex());
        bookChapterRepository.save(bookChapter);

        Book book = bookChapter.getBook();
        book.incrementVersion();
        bookRepository.save(book);

        return new ResponseEntity(new ApiResponse(true, "Book Chapter created successfully"), HttpStatus.CREATED);
    }
}