package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.BookChapter;
import in.co.qedtech.trappist.model.BookTopic;
import in.co.qedtech.trappist.model.QPT;
import in.co.qedtech.trappist.repository.BookChapterRepository;
import in.co.qedtech.trappist.repository.BookTopicRepository;

import in.co.qedtech.trappist.repository.QPTRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QPTController {
    private static final Logger logger = LoggerFactory.getLogger(QPTController.class);

    @Autowired private BookChapterRepository bookChapterRepository;
    @Autowired private BookTopicRepository bookTopicRepository;
    @Autowired private QPTRepository qptRepository;

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping("/get/qpts")
    public List<QPT> getQPTs() {
        return null;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/save/qptAfterBookTopic/{bookTopicId}")
    public List<BookTopic> saveQPTAfterBookTopic(@PathVariable(value = "bookTopicId") int bookTopicId) {
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);
        BookChapter bookChapter = bookTopic.getBookChapter();
        ArrayList<BookTopic> bookTopics = (ArrayList<BookTopic>) bookTopicRepository.findByBookChapter(bookChapter);

        bookTopics.sort(Comparator.comparingInt(BookTopic::getTopicIndex));

        QPT previousQPT = null, newQPT;

        for(BookTopic bt : bookTopics) {
            if(bt.getQpt() == null) {
                newQPT = new QPT();
                newQPT.setBookChapter(bookChapter);

                if(previousQPT == null) {
                    newQPT.setName("QPT " + 1);
                    newQPT.setQptIndex(1);
                }
                else {
                    newQPT.setName("QPT " + (previousQPT.getQptIndex() + 1));
                    newQPT.setQptIndex(previousQPT.getQptIndex() + 1);
                }

                QPT savedQPT = qptRepository.findByNameAndBookChapter(newQPT.getName(), newQPT.getBookChapter());
                if(savedQPT == null)
                    savedQPT = qptRepository.save(newQPT);

                bt.setQpt(savedQPT);
            }
            else {
                previousQPT = bt.getQpt();
            }

            if(bt == bookTopic) {
                bt.setDoesPrecedeQPT(true);
                bookTopicRepository.save(bt);
                bookChapter.incrementVersion();
                bookChapterRepository.save(bookChapter);

                break;
            }

            bookTopicRepository.save(bt);
            bookChapter.incrementVersion();
            bookChapterRepository.save(bookChapter);
        }

        return bookTopicRepository.findByBookChapter(bookChapter);
    }


    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/delete/qpt/{qptId}")
    public List<BookTopic> deleteQPT(@PathVariable(value = "qptId") int qptId) {
        return null;
    }
}