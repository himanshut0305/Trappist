package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.*;
import in.co.qedtech.trappist.payload.*;
import in.co.qedtech.trappist.repository.*;
import in.co.qedtech.trappist.security.CurrentUser;
import in.co.qedtech.trappist.security.UserPrincipal;
import in.co.qedtech.trappist.service.FileStorageService;
import in.co.qedtech.trappist.service.StringComparisonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api")
class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @Autowired private FileStorageService fileStorageService;
    @Autowired private TopicRepository topicRepository;
    @Autowired private DiagramRepository diagramRepository;

    @Autowired private OptionRepository optionRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private UserRepository userRepository;

    @Autowired private BookRepository bookRepository;
    @Autowired private BookChapterRepository bookChapterRepository;
    @Autowired private BookTopicRepository bookTopicRepository;

    @Autowired private TopicMapRepository topicMapRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value = "/save/question")
    public long saveQuestion(@CurrentUser UserPrincipal principal, @Valid @RequestBody SaveQuestionRequest request) {
        logger.error("Custom Debug Msg : " + request.toString());
        User currentUser = userRepository.getOne(principal.getId());

        Long id = request.getTopicId();
        Integer topicId = Integer.parseInt(id.toString());
        Topic topic = topicRepository.getOne(topicId);

        long savedQuestionId = 0;
        if (!request.getType().equals(QuestionType.Undetermined)) {
            Question question = new Question();

            question.setTopic(topic);

            question.setQuestion(request.getQuestion());
            question.setInstruction(request.getInstruction());
            question.setStatus(ContentStatus.SUBMITTED);

            question.setLevel(request.getLevel());
            question.setType(request.getType());
            question.setCategory(request.getCategory());

            question.setComment("<b>" + currentUser.getName() + "</b><br>" + request.getComment());

            HashSet<Option> options = new HashSet<>();
            for (OptionRequest optionRequest : request.getOption()) {
                Option option = new Option(optionRequest.getText(), optionRequest.isCorrect(), optionRequest.getExplanation());
                Option savedOption = optionRepository.save(option);
                options.add(savedOption);
            }

            question.setOptions(options);

            if (request.getDiagram() != null) {
                String diagramURI = fileStorageService.storeBufferedImage(request.getDiagram());
                Diagram diagram = new Diagram(diagramURI, "", null);
                diagramRepository.saveAndFlush(diagram);

                question.setDiagram(diagram);
            }

            savedQuestionId = questionRepository.save(question).getId();
        }

        if(request.getCurrentQuestionId() != null) {
            Question q = questionRepository.getOne(request.getCurrentQuestionId());
            questionRepository.delete(q);
        }

        return savedQuestionId;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value = "/get/similarQuestions")
    public List<Question> getSimilarQuestions(@Valid @RequestBody GetSimilarQuestionsRequest request) {
        StringComparisonService scc = new StringComparisonService();
        Topic topic = topicRepository.getOne(request.getTopicId());
        ArrayList<Question> questions = (ArrayList<Question>) questionRepository.findByTopic(topic);

        ArrayList<Question> similarQuestionsList = new ArrayList<>();

        for (Question question : questions) {
            if(scc.getSimilarity(question.getQuestion(), request.getQuestion()) >= 0.75) {
                similarQuestionsList.add(question);
            }
        }

        return similarQuestionsList;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping(value = "/get/question/{topicId}")
    public Question getQuestion(@CurrentUser UserPrincipal principal, @PathVariable(value = "topicId") int topicId) {
        User user = userRepository.getOne(principal.getId());
        Role role = user.getRoles().iterator().next();
        Topic topic = topicRepository.getOne(topicId);

        ArrayList<Question> questions = null;
        if(role.getName() == RoleName.ROLE_SUBJECT_EXPERT || role.getName() == RoleName.ROLE_SUPER_ADMIN) {
            questions = (ArrayList<Question>) questionRepository.findByTopic(topic);
        }
        else if(role.getName() == RoleName.ROLE_CONTRIBUTOR) {
            questions = (ArrayList<Question>) questionRepository.findByTopicAndCreatedBy(topic, user);
        }

        if(questions.size() > 0) {
            Question question = questions.get(0);
            for (int i = 1; i < questions.size(); i++) {
                if(question.getUpdatedAt().isAfter(questions.get(i).getUpdatedAt())) {
                    question = questions.get(i);
                }
            }

            return question;
        }

        return null;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping(value = "/get/questions/{topicId}")
    public List<Question> getQuestions(@CurrentUser UserPrincipal principal, @PathVariable(value = "topicId") int topicId) {
        User user = userRepository.getOne(principal.getId());
        Role role = user.getRoles().iterator().next();
        Topic topic = topicRepository.getOne(topicId);

        ArrayList<Question> questions = null;
        if(role.getName() == RoleName.ROLE_SUBJECT_EXPERT || role.getName() == RoleName.ROLE_SUPER_ADMIN) {
            questions = (ArrayList<Question>) questionRepository.findByTopic(topic);
        }
        else if(role.getName() == RoleName.ROLE_CONTRIBUTOR) {
            questions = (ArrayList<Question>) questionRepository.findByTopicAndCreatedBy(topic, user);
        }

        return questions;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping(value = "/get/questionById/{questionId}")
    public Question getQuestionById(@CurrentUser UserPrincipal principal, @PathVariable(value = "questionId") int questionId) {
        return questionRepository.getOne(questionId);
    }


    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT", "ROLE_TEACHER"})
    @PostMapping(value="/get/questionsForTopic")
    public ArrayList<Question> getSlidesForTopic(@Valid @RequestBody GetSlidesForTopicRequest request) {
        Book book = bookRepository.findByName(request.getBookName());
        BookChapter bookChapter = bookChapterRepository.findByNameAndBook(request.getBookChapterName(), book);
        BookTopic bookTopic = bookTopicRepository.findByNameAndBookChapter(request.getBookTopicName(), bookChapter);

        List<TopicBookTopicMap> topicMaps = topicMapRepository.findByBookTopic(bookTopic);

        ArrayList<Question> questions = new ArrayList<>();

        for (TopicBookTopicMap tm : topicMaps) {
            List<Question> qs = questionRepository.findByTopic(tm.getTopic());
            questions.addAll(qs);
        }

        return questions;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping(value = "/delete/question/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable(value = "questionId") int questionId) {
        Question question = questionRepository.getOne(questionId);
        questionRepository.delete(question);
        return new ResponseEntity(new ApiResponse(true, "Deleted question successfully"), HttpStatus.CREATED);
    }
}