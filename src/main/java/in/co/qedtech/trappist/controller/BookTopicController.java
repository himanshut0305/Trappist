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
class BookTopicController {
    private static final Logger logger = LoggerFactory.getLogger(BookTopicController.class);

    @Autowired BookRepository bookRepository;
    @Autowired BookChapterRepository bookChapterRepository;
    @Autowired BookTopicRepository bookTopicRepository;

    @Autowired SlideRepository slideRepository;
    @Autowired TopicRepository topicRepository;
    @Autowired TopicMapRepository topicMapRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/bookTopics")
    public List<BookTopic> getBookTopics() {
        List<BookTopic> bookTopics = bookTopicRepository.findAll();
        return bookTopics;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/get/bookTopicsByBookChapter")
    public List<BookTopic> getBookTopicsByBookChapter(@Valid @RequestBody GetBookTopicsRequest getBookTopicsRequest) {
        Long id = getBookTopicsRequest.getBookChapterId();
        Integer bookChapterId = Integer.parseInt(id.toString());
        BookChapter bookChapter = bookChapterRepository.getOne(bookChapterId);

        List<BookTopic> bookTopicList = bookTopicRepository.findByBookChapter(bookChapter);
        return bookTopicList;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT"})
    @PostMapping("/get/bookTopicsByBookAndChapterName")
    public List<BookTopic> getBookTopicsByBookAndChapterName(@Valid @RequestBody GetBookTopicsByBookAndChapterNameRequest request) {
        Book book = bookRepository.findByName(request.getBookName());
        BookChapter bookChapter = bookChapterRepository.findByNameAndBook(request.getChapterName(), book);

        return bookTopicRepository.findByBookChapter(bookChapter);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT"})
    @PostMapping("/get/bookTopicsForChapterIfUpdated")
    public List<BookTopic> getBookTopicsIfUpdated(@Valid @RequestBody GetBookTopicsByBookAndChapterNameRequest request) {
        Book book = bookRepository.findByName(request.getBookName());
        BookChapter bookChapter = bookChapterRepository.findByNameAndBook(request.getChapterName(), book);

        if(bookChapter.getVersion() > request.getVersion()) {
            return bookTopicRepository.findByBookChapter(bookChapter);
        }
        else {
            return null;
        }
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/save/bookTopic")
    public ResponseEntity<?> saveBookTopic(@Valid @RequestBody SaveBookTopicRequest saveBookTopicRequest) {
        logger.info(saveBookTopicRequest.toString());

        Long id = saveBookTopicRequest.getBookChapterId();
        Integer bookChapterId = Integer.parseInt(id.toString());
        BookChapter bookChapter = bookChapterRepository.getOne(bookChapterId);

        if(bookTopicRepository.existsByNameAndBookChapter(saveBookTopicRequest.getName(), bookChapter)) {
            return new ResponseEntity(new ApiResponse(false, "Topic already exists"), HttpStatus.BAD_REQUEST);
        }

        BookTopic bookTopic = new BookTopic(saveBookTopicRequest.getName(), saveBookTopicRequest.getTopicIndex(), bookChapter, saveBookTopicRequest.getDescription());
        bookTopicRepository.save(bookTopic);

        bookChapter.incrementVersion();
        bookChapterRepository.save(bookChapter);

        return new ResponseEntity(new ApiResponse(true, "Book Topic created successfully"), HttpStatus.CREATED);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/update/bookTopic")
    public ResponseEntity<?> updateBookTopic(@Valid @RequestBody UpdateBookTopicRequest updateBookTopicRequest) {
        Integer id = updateBookTopicRequest.getBookTopicId();
        BookTopic bookTopic = bookTopicRepository.getOne(id);

        bookTopic.setName(updateBookTopicRequest.getBookTopicName());
        bookTopic.setTopicIndex(updateBookTopicRequest.getBookTopicIndex());
        bookTopic.setDescription(updateBookTopicRequest.getDescription());

        bookTopicRepository.save(bookTopic);

        BookChapter bookChapter = bookTopic.getBookChapter();
        bookChapter.incrementVersion();
        bookChapterRepository.save(bookChapter);

        return new ResponseEntity(new ApiResponse(true, "Book Topic updated successfully"), HttpStatus.CREATED);
    }

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping(value="/delete/bookTopic/{bookTopicId}")
    public ResponseEntity<?> deleteBookTopic(@PathVariable(value = "bookTopicId") int bookTopicId) {
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);
        List<RevisionSlide> slides = slideRepository.findByBookTopic(bookTopic);
        if(slides.size() > 0) {
            return new ResponseEntity(new ApiResponse(false, "Topic has "+ slides.size() + " Slides. Cannot delete."), HttpStatus.NOT_IMPLEMENTED);
        }
        else {
            BookChapter bookChapter = bookTopic.getBookChapter();
            bookTopicRepository.delete(bookTopic);
            bookChapter.incrementVersion();
            bookChapterRepository.save(bookChapter);

            return new ResponseEntity(new ApiResponse(true, "Topic deleted successfully"), HttpStatus.OK);
        }
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/link/bookTopicToTopics")
    public List<TopicBookTopicMap> linkBookTopicToTopics(@Valid @RequestBody LinkBookTopicToTopicRequest request) {
        logger.info("LBTTTR :" + request.toString());

        BookTopic bookTopic = bookTopicRepository.getOne(request.getBookTopicId());
        Topic topic = topicRepository.getOne(request.getTopicId());

        TopicBookTopicMap topicMap = new TopicBookTopicMap(topic, bookTopic);
        topicMapRepository.save(topicMap);

        return topicMapRepository.findByBookTopic(bookTopic);
    }
}