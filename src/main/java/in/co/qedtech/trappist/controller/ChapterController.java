package in.co.qedtech.trappist.controller;

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

import in.co.qedtech.trappist.model.Chapter;
import in.co.qedtech.trappist.model.Subject;
import in.co.qedtech.trappist.model.Topic;
import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.payload.GetChaptersRequest;
import in.co.qedtech.trappist.payload.SaveChapterRequest;
import in.co.qedtech.trappist.repository.ChapterRepository;
import in.co.qedtech.trappist.repository.SubjectRepository;
import in.co.qedtech.trappist.repository.TopicRepository;

@RestController
@RequestMapping("/api")
class ChapterController {
    private static final Logger logger = LoggerFactory.getLogger(ChapterController.class);

    @Autowired private SubjectRepository subjectRepository;
    @Autowired private ChapterRepository chapterRepository;
    @Autowired private TopicRepository topicRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/chapters")
    public List<Chapter> getChapters() {
        List<Chapter> chapterList = chapterRepository.findAll();
        return chapterList;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/get/chaptersBySubject")
    public List<Chapter> getChaptersBySubject(@Valid @RequestBody GetChaptersRequest getChaptersRequest) {
        Long id = getChaptersRequest.getSubjectId();
        Integer iid = Integer.parseInt(id.toString());

        Subject subject = subjectRepository.getOne(iid);
        List<Chapter> chapterList = chapterRepository.findBySubject(subject);
        return chapterList;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/save/chapter")
    public ResponseEntity<?> saveChapter(@Valid @RequestBody SaveChapterRequest saveChapterRequest) {
        Long id = saveChapterRequest.getSubjectId();
        Integer subjectId = Integer.parseInt(id.toString());
        Subject subject = subjectRepository.getOne(subjectId);

        if(chapterRepository.existsByNameAndSubject(saveChapterRequest.getName(), subject)) {
            return new ResponseEntity(new ApiResponse(false, "Name already exists"), HttpStatus.BAD_REQUEST);
        }

        Chapter chapter = new Chapter(saveChapterRequest.getName(), subject);
        chapterRepository.save(chapter);

        return new ResponseEntity(new ApiResponse(true, "Topic created successfully"), HttpStatus.CREATED);
    }

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping(value="/delete/chapter/{chapterId}")
    public ResponseEntity<?> deleteChapter(@PathVariable(value = "chapterId") int chapterId) {
        Chapter chapter = chapterRepository.getOne(chapterId);
        List<Topic> topics = topicRepository.findByChapter(chapter);
        if(topics.size() > 0) {
            return new ResponseEntity(new ApiResponse(false, "Chapter has child topics. Cannot delete."), HttpStatus.NOT_IMPLEMENTED);
        }
        else {
            chapterRepository.delete(chapter);
            return new ResponseEntity(new ApiResponse(true, "Chapter deleted successfully"), HttpStatus.OK);
        }
    }
}
