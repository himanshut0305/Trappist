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

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
class BookController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired private SubjectRepository subjectRepository;
    @Autowired private SchoolYearRepository schoolYearRepository;
    @Autowired private BookRepository bookRepository;

    @Autowired private YearRepository yearRepository;
    @Autowired private SubjectYearRepository subjectYearRepository;
    @Autowired private BookChapterRepository bookChapterRepository;

    @Autowired private SchoolRepository schoolRepository;
    @Autowired private SchoolYearSubjectBookRepository schoolYearSubjectBookRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/books")
    public List<Book> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/get/booksByYearAndSubject")
    public List<Book> getBooksByYearAndSubject(@Valid @RequestBody GetBooksRequest getBooksRequest) {
        Subject subject = subjectRepository.getOne(getBooksRequest.getSubjectId());
        Year year = yearRepository.getOne(getBooksRequest.getSchoolYearId());
        SubjectYear subjectYear = subjectYearRepository.findBySubjectAndYear(subject, year);

        List<Book> bookList = bookRepository.findBySubjectYear(subjectYear);
        return bookList;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/booksByYear/{yearId}")
    public List<Book> getBooksByYear(@PathVariable(value = "yearId") int yearId) {
        Year year = yearRepository.getOne(yearId);
        ArrayList<SubjectYear> subjectYears = (ArrayList<SubjectYear>) subjectYearRepository.findByYear(year);

        ArrayList<Book> bookList = new ArrayList<>();
        for (SubjectYear subjectYear:subjectYears) {
            List<Book> books = bookRepository.findBySubjectYear(subjectYear);
            bookList.addAll(books);
        }

        return bookList;
    }

    @Secured("ROLE_SUPER_ADMIN")
    @PostMapping(value="/save/book")
    public ResponseEntity<?> saveBook(@Valid @RequestBody SaveBookRequest request) {
        if(bookRepository.existsByName(request.getName())) {
            return new ResponseEntity(new ApiResponse(false, "Name already exists"), HttpStatus.BAD_REQUEST);
        }

        Subject subject = subjectRepository.getOne(request.getSubjectId());
        Year year = yearRepository.getOne(request.getSchoolYearId());

        SubjectYear subjectYear = subjectYearRepository.findBySubjectAndYear(subject, year);
        Book book = new Book(request.getName(), subjectYear);
        bookRepository.save(book);

        return new ResponseEntity(new ApiResponse(true, "Book created successfully"), HttpStatus.CREATED);
    }

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping(value="/delete/book/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable(value = "bookId") int bookId) {
        Book book = bookRepository.getOne(bookId);
        List<BookChapter> bookChapters = bookChapterRepository.findByBook(book);
        if(bookChapters.size() > 0) {
            return new ResponseEntity(new ApiResponse(false, "Book has "+ bookChapters.size() + " chapters. Cannot delete."), HttpStatus.NOT_IMPLEMENTED);
        }
        else {
            bookRepository.delete(book);
            return new ResponseEntity(new ApiResponse(true, "Chapter deleted successfully"), HttpStatus.OK);
        }
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT"})
    @PostMapping("/get/bookBySubjectSchoolYear")
    public Book getBookChaptersBySubjectSchoolYear(@Valid @RequestBody GetBookBySubjectSchoolYearRequest request) {
        School school = schoolRepository.findBySchoolCode(request.getSchoolCode());
        Subject subject = subjectRepository.findByName(request.getSubjectName());
        Year year = yearRepository.findByName(request.getYear());

        SchoolYear schoolYear = schoolYearRepository.findBySchoolAndYear(school, year);
        SchoolYearSubjectBook sysb = schoolYearSubjectBookRepository.findBySchoolYearAndSubject(schoolYear, subject);

        return sysb.getBook();
    }
}