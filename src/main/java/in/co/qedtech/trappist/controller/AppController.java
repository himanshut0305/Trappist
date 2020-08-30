package in.co.qedtech.trappist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import in.co.qedtech.trappist.model.Book;
import in.co.qedtech.trappist.repository.BookRepository;

@RestController
@RequestMapping("/api")
class AppController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired BookRepository bookRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/app/get/books")
    public List<Book> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books;
    }
}
