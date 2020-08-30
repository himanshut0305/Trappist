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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import in.co.qedtech.trappist.model.Chapter;
import in.co.qedtech.trappist.model.Topic;
import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.payload.GetTopicsRequest;
import in.co.qedtech.trappist.payload.SaveTopicRequest;
import in.co.qedtech.trappist.repository.ChapterRepository;
import in.co.qedtech.trappist.repository.TopicRepository;

@RestController
@RequestMapping("/api")
class TopicController {
    private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

    @Autowired ChapterRepository chapterRepository;
    @Autowired TopicRepository topicRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/topics")
    public List<Topic> getTopics() {
        List<Topic> topicList = topicRepository.findAll();
        return topicList;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/get/topicsByChapter")
    public List<Topic> getTopicsByChapter(@Valid @RequestBody GetTopicsRequest getTopicsRequest) {
        Long id = getTopicsRequest.getChapterId();
        Integer iid = Integer.parseInt(id.toString());

        Chapter chapter = chapterRepository.getOne(iid);
        List<Topic> topicList = topicRepository.findByChapter(chapter);
        return topicList;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/save/topic")
    public ResponseEntity<?> saveTopic(@Valid @RequestBody SaveTopicRequest saveTopicRequest) {
        Long id = saveTopicRequest.getChapterId();
        Integer chapterId = Integer.parseInt(id.toString());
        Chapter chapter = chapterRepository.getOne(chapterId);

        if(topicRepository.existsByNameAndChapter(saveTopicRequest.getName(), chapter)) {
            return new ResponseEntity(new ApiResponse(false, "Name already exists"), HttpStatus.BAD_REQUEST);
        }

        Topic topic = new Topic(saveTopicRequest.getName(), chapter);
        topicRepository.save(topic);

        return new ResponseEntity(new ApiResponse(true, "Topic created successfully"), HttpStatus.CREATED);
    }

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping(value="/delete/topic/{topicId}")
    public ResponseEntity<?> deleteTopic(@PathVariable(value = "topicId") int topicId) {
        Topic topic = topicRepository.getOne(topicId);
        topicRepository.delete(topic);

        return new ResponseEntity(new ApiResponse(true, "Topic deleted successfully"), HttpStatus.OK);
    }
}