package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.*;
import in.co.qedtech.trappist.model.Diagram;
import in.co.qedtech.trappist.payload.*;
import in.co.qedtech.trappist.repository.*;
import in.co.qedtech.trappist.slideResponse.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import in.co.qedtech.trappist.security.CurrentUser;
import in.co.qedtech.trappist.security.UserPrincipal;
import in.co.qedtech.trappist.service.FileStorageService;

@RestController
@RequestMapping("/api")
class SlidesController {
    private static final Logger logger = LoggerFactory.getLogger(SlidesController.class);

    @Autowired private FileStorageService fileStorageService;
    @Autowired private TopicRepository topicRepository;
    @Autowired private SlideRepository slideRepository;

    @Autowired private RevisionPointRepository revisionPointRepository;
    @Autowired private DiagramRepository diagramRepository;
    @Autowired private ConfirmInteractionRepository confirmInteractionRepository;
    @Autowired private RevealInteractionRepository revealInteractionRepository;

    @Autowired private OptionRepository optionRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private UserRepository userRepository;

    @Autowired private BookRepository bookRepository;
    @Autowired private BookChapterRepository bookChapterRepository;
    @Autowired private BookTopicRepository bookTopicRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/save/revisionSlide")
    public ResponseEntity<?> saveRevisionSlide(@CurrentUser UserPrincipal principal, @Valid @RequestBody SaveRevisionSlideRequest saveRevisionSlideRequest) {
        logger.info("Custom Debug Msg : " + saveRevisionSlideRequest.toString());

        User currentUser = userRepository.getOne(principal.getId());

        Long id = saveRevisionSlideRequest.getTopicId();
        Integer bookTopicId = Integer.parseInt(id.toString());
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);

        RevisionSlide savedSlide = null;

        savedSlide = slideRepository.findByBookTopicAndSlideIndex(bookTopic, saveRevisionSlideRequest.getSlideIndex());

        if(savedSlide == null) {
            RevisionSlide slide = new RevisionSlide(saveRevisionSlideRequest.getSlideIndex(), bookTopic, currentUser, RevisionSlideType.RevisionSlide);
            slide.setComment("<b>" + currentUser.getName() +  "</b><br>" + saveRevisionSlideRequest.getComment());
            savedSlide = slideRepository.save(slide);
        }
        else {
            savedSlide.setUpdatedBy(currentUser);
            String cmmnt = savedSlide.getComment() + "<br><br><b>" + currentUser.getName() +  "</b><br>" + saveRevisionSlideRequest.getComment();
            savedSlide.setComment(cmmnt);
            ArrayList<RevisionPoint> revisionPoints = (ArrayList<RevisionPoint>) revisionPointRepository.findByRevisionSlide(savedSlide);

            ArrayList<RevealInteraction> revealInteractions = (ArrayList<RevealInteraction>) revealInteractionRepository.findByRevisionSlide(savedSlide);
            ArrayList<ConfirmInteraction> confirmInteractions = (ArrayList<ConfirmInteraction>) confirmInteractionRepository.findByRevisionSlide(savedSlide);
            ArrayList<Diagram> diagrams = (ArrayList<Diagram>) diagramRepository.findByRevisionSlide(savedSlide);

            for (RevisionPoint point: revisionPoints) {
                point.setStatus(ContentStatus.DELETED);
                revisionPointRepository.save(point);
            }

            for(RevealInteraction revealInteraction : revealInteractions) {
                revealInteraction.setStatus(ContentStatus.DELETED);
                revealInteractionRepository.delete(revealInteraction);
            }

            for(ConfirmInteraction confirmInteraction : confirmInteractions) {
                confirmInteraction.setStatus(ContentStatus.DELETED);
                confirmInteractionRepository.delete(confirmInteraction);
            }

            for(Diagram diagram : diagrams) {
                diagram.setStatus(ContentStatus.DELETED);
                diagramRepository.delete(diagram);
            }
        }

        if (!saveRevisionSlideRequest.getPreface1().equals("") || !saveRevisionSlideRequest.getExplanation1().equals("")) {
            RevisionPoint revisionPoint = new RevisionPoint(1, saveRevisionSlideRequest.getPreface1(), saveRevisionSlideRequest.getExplanation1(), savedSlide);
            revisionPointRepository.save(revisionPoint);
        }

        if (!saveRevisionSlideRequest.getPreface2().equals("") || !saveRevisionSlideRequest.getExplanation2().equals("")) {
            RevisionPoint revisionPoint = new RevisionPoint(2, saveRevisionSlideRequest.getPreface2(), saveRevisionSlideRequest.getExplanation2(), savedSlide);
            revisionPointRepository.save(revisionPoint);
        }
        else if (!saveRevisionSlideRequest.getRiQuestion().equals("") || !saveRevisionSlideRequest.getRiAnswer().equals("")) {
            RevealInteraction revealInteraction = new RevealInteraction(saveRevisionSlideRequest.getRiQuestion(), saveRevisionSlideRequest.getRiAnswer(), savedSlide);
            revealInteractionRepository.save(revealInteraction);
        }
        else if (!saveRevisionSlideRequest.getCiQuestion().equals("")) {
            ConfirmInteraction confirmInteraction = new ConfirmInteraction(saveRevisionSlideRequest.getCiQuestion(), "Yes", "No", savedSlide);
            confirmInteractionRepository.save(confirmInteraction);
        }
        else if (!saveRevisionSlideRequest.getDiagramCaption().equals("")) {
            String diagramURI = fileStorageService.storeBufferedImage(saveRevisionSlideRequest.getDiagram());
            Diagram diagram = new Diagram(diagramURI, saveRevisionSlideRequest.getDiagramCaption(), savedSlide);
            diagramRepository.save(diagram);
        }

        slideRepository.saveAndFlush(savedSlide);

        bookTopic.incrementVersion();
        bookTopicRepository.save(bookTopic);
        bookTopic.getBookChapter().incrementVersion();
        bookChapterRepository.save(bookTopic.getBookChapter());

        return new ResponseEntity(new ApiResponse(true, saveRevisionSlideRequest.toString()), HttpStatus.CREATED);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/save/questionSlide")
    public ResponseEntity<?> saveQuestionSlide(@CurrentUser UserPrincipal principal, @Valid @RequestBody SaveQuestionSlideRequest saveQuestionSlideRequest) {
        logger.error("Custom Debug Msg : " + saveQuestionSlideRequest.toString());
        User currentUser = userRepository.getOne(principal.getId());

        Long id = saveQuestionSlideRequest.getTopicId();
        Integer bookTopicId = Integer.parseInt(id.toString());
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);

        RevisionSlide savedSlide = null;

        savedSlide = slideRepository.findByBookTopicAndSlideIndex(bookTopic, saveQuestionSlideRequest.getSlideIndex());

        if(savedSlide == null) {
            RevisionSlide slide = new RevisionSlide(saveQuestionSlideRequest.getSlideIndex(), bookTopic, currentUser, RevisionSlideType.QuestionSlide);
            slide.setComment("<b>" + currentUser.getName() +  "</b><br>" + saveQuestionSlideRequest.getComment());
            slide.setHasInteractiveQuestion(saveQuestionSlideRequest.isInteractive());

            savedSlide = slideRepository.save(slide);
        }
        else {
            savedSlide.setType(RevisionSlideType.QuestionSlide);
            savedSlide.setUpdatedBy(currentUser);
            String cmmnt = savedSlide.getComment() + "<br><br><b>" + currentUser.getName() +  "</b><br>" + saveQuestionSlideRequest.getComment();
            savedSlide.setComment(cmmnt);

            savedSlide.setHasInteractiveQuestion(saveQuestionSlideRequest.isInteractive());

            slideRepository.saveAndFlush(savedSlide);

            Question oldQuestion = questionRepository.findByRevisionSlideAndStatus(savedSlide, ContentStatus.SUBMITTED);
            if(oldQuestion != null) {
                oldQuestion.setStatus(ContentStatus.DELETED);
                questionRepository.save(oldQuestion);
            }
        }

        if(!saveQuestionSlideRequest.getType().equals(QuestionType.Undetermined)){
            Question question = new Question();

            question.setRevisionSlide(savedSlide);
            question.setQuestion(saveQuestionSlideRequest.getQuestion());
            question.setInstruction(saveQuestionSlideRequest.getInstruction());
            question.setStatus(ContentStatus.SUBMITTED);

            HashSet<Option> options = new HashSet<>();
            for (OptionRequest optionRequest:saveQuestionSlideRequest.getOption()) {
                Option option = new Option(optionRequest.getText(), optionRequest.isCorrect(), optionRequest.getExplanation());
                Option savedOption = optionRepository.save(option);
                options.add(savedOption);
            }

            question.setOptions(options);

            if(saveQuestionSlideRequest.getDiagram() != null) {
                String diagramURI = fileStorageService.storeBufferedImage(saveQuestionSlideRequest.getDiagram());
                Diagram diagram = new Diagram(diagramURI, "", null);
                diagramRepository.saveAndFlush(diagram);

                question.setDiagram(diagram);
            }

            questionRepository.save(question);
        }

        bookTopic.incrementVersion();
        bookTopicRepository.save(bookTopic);
        bookTopic.getBookChapter().incrementVersion();
        bookChapterRepository.save(bookTopic.getBookChapter());

        return new ResponseEntity(new ApiResponse(true, saveQuestionSlideRequest.toString()), HttpStatus.CREATED);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/get/questionSlide")
    public GetQuestionSlideResponse getQuestionSlide(@Valid @RequestBody GetQuestionSlideRequest getQuestionSlideRequest) {
        Long id = getQuestionSlideRequest.getTopicId();
        Integer bookTopicId = Integer.parseInt(id.toString());
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);

        RevisionSlide slide = slideRepository.findByBookTopicAndSlideIndex(bookTopic, getQuestionSlideRequest.getSlideIndex());
        long numberOfSlides = slideRepository.countByBookTopic(bookTopic);

        GetQuestionSlideResponse response = new GetQuestionSlideResponse();

        if(slide != null) {
            ArrayList<Question> qs = (ArrayList<Question>) questionRepository.findByRevisionSlide(slide);
            Question question = null;

            for (Question q : qs) {
                if(q.getStatus() != ContentStatus.DELETED) {
                    question = q;
                    break;
                }
            }

            if(question != null) {
                response.setComment(slide.getComment());

                response.setQuestion(question.getQuestion());
                response.setInstruction(question.getInstruction());
                response.setQuestionType(question.getQuestionType());
                response.setDiagramURL(question.getDiagram() == null ? "" : question.getDiagram().getUrl());
                response.setOption(question.getOptions());
            }

            response.setSlideId((int) slide.getId());
        }
        else {
            response.setSlideId(0);
        }

        response.setCreatedBy(slide == null ? "" : slide.getCreatedBy().getName());
        response.setReviewedBy(slide == null ? "" : slide.getUpdatedBy().getName());
        response.setComment(slide == null ? "" : slide.getComment());

        response.setTopicId(getQuestionSlideRequest.getTopicId());
        response.setSlideIndex(getQuestionSlideRequest.getSlideIndex());
        response.setNumberOfSlides(numberOfSlides);

        return response;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/get/revisionSlide")
    public GetRevisionSlideResponse getRevisionSlide(@Valid @RequestBody GetRevisionSlideRequest getRevisionSlideRequest) {
        Long id = getRevisionSlideRequest.getTopicId();
        Integer bookTopicId = Integer.parseInt(id.toString());
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);

        RevisionSlide slide = slideRepository.findByBookTopicAndSlideIndex(bookTopic, getRevisionSlideRequest.getSlideIndex());
        RevisionSlide nextSlide = slideRepository.findByBookTopicAndSlideIndex(bookTopic, getRevisionSlideRequest.getSlideIndex() + 1);
        long numberOfSlides = slideRepository.countByBookTopic(bookTopic);

        GetRevisionSlideResponse getRevisionSlideResponse = null;

        if(slide == null) {
            getRevisionSlideResponse = new GetRevisionSlideResponse();

            getRevisionSlideResponse.setSlideIndex(getRevisionSlideRequest.getSlideIndex());
            getRevisionSlideResponse.setTopicId(getRevisionSlideRequest.getTopicId());
            getRevisionSlideResponse.setSlideType(RevisionSlideType.RevisionSlide);
            getRevisionSlideResponse.setLastSlide(true);
            getRevisionSlideResponse.setSlideId(0);

            return getRevisionSlideResponse;
        }
        else if(slide.getType() != RevisionSlideType.QuestionSlide) {
            ArrayList<RevisionPoint> rps = (ArrayList<RevisionPoint>) revisionPointRepository.findByRevisionSlide(slide);
            ArrayList<RevisionPoint> revisionPoints = new ArrayList<>();
            for (RevisionPoint revisionPoint : rps) {
                if (revisionPoint.getStatus() != ContentStatus.DELETED)
                    revisionPoints.add(revisionPoint);
            }

            ArrayList<RevealInteraction> ris = (ArrayList<RevealInteraction>) revealInteractionRepository.findByRevisionSlide(slide);
            ArrayList<RevealInteraction> revealInteractions = new ArrayList<>();
            for (RevealInteraction interaction : ris) {
                if (interaction.getStatus() != ContentStatus.DELETED)
                    revealInteractions.add(interaction);
            }

            ArrayList<ConfirmInteraction> cis = (ArrayList<ConfirmInteraction>) confirmInteractionRepository.findByRevisionSlide(slide);
            ArrayList<ConfirmInteraction> confirmInteractions = new ArrayList<>();
            for (ConfirmInteraction interaction : cis) {
                if (interaction.getStatus() != ContentStatus.DELETED)
                    confirmInteractions.add(interaction);
            }

            ArrayList<Diagram> digs = (ArrayList<Diagram>) diagramRepository.findByRevisionSlide(slide);
            ArrayList<Diagram> diagrams = new ArrayList<>();
            for (Diagram diagram : digs) {
                if (diagram.getStatus() != ContentStatus.DELETED)
                    diagrams.add(diagram);
            }

            RevealInteraction revealInteraction = null;
            if (revealInteractions.size() == 1) {
                revealInteraction = revealInteractions.get(0);
            }

            ConfirmInteraction confirmInteraction = null;
            if (confirmInteractions.size() == 1) {
                confirmInteraction = confirmInteractions.get(0);
            }

            Diagram diagram = null;
            if (diagrams.size() == 1) {
                diagram = diagrams.get(0);
            }

            if (revisionPoints.size() == 2) {
                getRevisionSlideResponse = new GetRevisionSlideResponse(revisionPoints.get(0), revisionPoints.get(1));
                logger.info("Has 2 Revision Points : ", getRevisionSlideResponse.toString());
            } else if (revisionPoints.size() == 1) {
                if (revealInteraction != null) {
                    getRevisionSlideResponse = new GetRevisionSlideResponse(revisionPoints.get(0), revealInteraction);
                } else if (confirmInteraction != null) {
                    getRevisionSlideResponse = new GetRevisionSlideResponse(revisionPoints.get(0), confirmInteraction);
                } else if (diagram != null) {
                    getRevisionSlideResponse = new GetRevisionSlideResponse(revisionPoints.get(0), diagram);
                } else {
                    getRevisionSlideResponse = new GetRevisionSlideResponse(revisionPoints.get(0));
                }

                logger.info("Has 1 Revision Point");
            } else if (revisionPoints.size() == 0) {
                if (revealInteraction != null) {
                    getRevisionSlideResponse = new GetRevisionSlideResponse(revealInteraction);
                } else if (confirmInteraction != null) {
                    getRevisionSlideResponse = new GetRevisionSlideResponse(confirmInteraction);
                } else if (diagram != null) {
                    getRevisionSlideResponse = new GetRevisionSlideResponse(diagram);
                } else {
                    getRevisionSlideResponse = new GetRevisionSlideResponse();
                }

                logger.info("Has 0 Revision Point");
            }

            getRevisionSlideResponse.setSlideIndex(getRevisionSlideRequest.getSlideIndex());
            getRevisionSlideResponse.setTopicId(getRevisionSlideRequest.getTopicId());
            getRevisionSlideResponse.setSlideType(RevisionSlideType.RevisionSlide);

            getRevisionSlideResponse.setComment(slide == null ? "" : slide.getComment());
            getRevisionSlideResponse.setCreatedBy(slide == null ? "" : slide.getCreatedBy().getName());
            getRevisionSlideResponse.setReviewedBy(slide == null ? "" : slide.getUpdatedBy().getName());

            getRevisionSlideResponse.setSlideId((int) slide.getId());
            if(nextSlide == null)
                getRevisionSlideResponse.setLastSlide(true);
            else
                getRevisionSlideResponse.setLastSlide(false);

            getRevisionSlideResponse.setNumberOfSlides(numberOfSlides);
            return getRevisionSlideResponse;
        }
        else {
            getRevisionSlideResponse = new GetRevisionSlideResponse();

            getRevisionSlideResponse.setSlideIndex(getRevisionSlideRequest.getSlideIndex());
            getRevisionSlideResponse.setTopicId(getRevisionSlideRequest.getTopicId());
            getRevisionSlideResponse.setSlideType(RevisionSlideType.QuestionSlide);

            if(nextSlide == null)
                getRevisionSlideResponse.setLastSlide(true);
            else
                getRevisionSlideResponse.setLastSlide(false);

            getRevisionSlideResponse.setNumberOfSlides(numberOfSlides);
            return getRevisionSlideResponse;
        }
    }

    @GetMapping("/get/diagram/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        }
        catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/delete/revisionSlide")
    public ResponseEntity<?> deleteRevisionSlide(@Valid @RequestBody GetRevisionSlideRequest getRevisionSlideRequest) {
        Long id = getRevisionSlideRequest.getTopicId();
        Integer bookTopicId = Integer.parseInt(id.toString());
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);

        RevisionSlide slide = slideRepository.findByBookTopicAndSlideIndex(bookTopic, getRevisionSlideRequest.getSlideIndex());

        if(slide.getType() == RevisionSlideType.RevisionSlide) {
            ArrayList<RevisionPoint> rps = (ArrayList<RevisionPoint>) revisionPointRepository.findByRevisionSlide(slide);
            ArrayList<RevealInteraction> ris = (ArrayList<RevealInteraction>) revealInteractionRepository.findByRevisionSlide(slide);
            ArrayList<ConfirmInteraction> cis = (ArrayList<ConfirmInteraction>) confirmInteractionRepository.findByRevisionSlide(slide);
            ArrayList<Diagram> digs = (ArrayList<Diagram>) diagramRepository.findByRevisionSlide(slide);

            revisionPointRepository.deleteAll(rps);
            revealInteractionRepository.deleteAll(ris);
            confirmInteractionRepository.deleteAll(cis);
            diagramRepository.deleteAll(digs);
        }
        else if(slide.getType() == RevisionSlideType.QuestionSlide) {
            ArrayList<Question> questions = (ArrayList<Question>) questionRepository.findByRevisionSlide(slide);
            questionRepository.deleteAll(questions);
        }

        slideRepository.delete(slide);

        ArrayList<RevisionSlide> revisionSlides = (ArrayList<RevisionSlide>) slideRepository.findByBookTopic(bookTopic);
        for (RevisionSlide revisionSlide:revisionSlides) {
            if(revisionSlide.getSlideIndex() > getRevisionSlideRequest.getSlideIndex()) {
                revisionSlide.setSlideIndex(revisionSlide.getSlideIndex() - 1);
                slideRepository.saveAndFlush(revisionSlide);
            }
        }

        bookTopic.incrementVersion();
        bookTopicRepository.save(bookTopic);
        bookTopic.getBookChapter().incrementVersion();
        bookChapterRepository.save(bookTopic.getBookChapter());

        return new ResponseEntity(new ApiResponse(true, "Deleted Successfully"), HttpStatus.OK);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/insertSlide/before")
    public ResponseEntity<?> insertBefore(@CurrentUser UserPrincipal principal, @Valid @RequestBody GetRevisionSlideRequest getRevisionSlideRequest) {
        User u = userRepository.getOne(principal.getId());

        Long id = getRevisionSlideRequest.getTopicId();
        Integer bookTopicId = Integer.parseInt(id.toString());
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);

        ArrayList<RevisionSlide> revisionSlides = (ArrayList<RevisionSlide>) slideRepository.findByBookTopic(bookTopic);
        for (RevisionSlide revisionSlide:revisionSlides) {
            if(revisionSlide.getSlideIndex() >= getRevisionSlideRequest.getSlideIndex()) {
                revisionSlide.setSlideIndex(revisionSlide.getSlideIndex() + 1);
                slideRepository.saveAndFlush(revisionSlide);
            }
        }

        RevisionSlide revisionSlide = new RevisionSlide(getRevisionSlideRequest.getSlideIndex(), bookTopic, u, RevisionSlideType.RevisionSlide);
        revisionSlide.setComment("Inserted after lesson creation");
        slideRepository.saveAndFlush(revisionSlide);

        bookTopic.incrementVersion();
        bookTopicRepository.save(bookTopic);
        bookTopic.getBookChapter().incrementVersion();
        bookChapterRepository.save(bookTopic.getBookChapter());

        return new ResponseEntity(new ApiResponse(true, "Deleted Successfully"), HttpStatus.OK);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/insertSlide/after")
    public ResponseEntity<?> insertAfter(@CurrentUser UserPrincipal principal, @Valid @RequestBody GetRevisionSlideRequest getRevisionSlideRequest) {
        User u = userRepository.getOne(principal.getId());

        Long id = getRevisionSlideRequest.getTopicId();
        Integer bookTopicId = Integer.parseInt(id.toString());
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);

        ArrayList<RevisionSlide> revisionSlides = (ArrayList<RevisionSlide>) slideRepository.findByBookTopic(bookTopic);
        for (RevisionSlide revisionSlide:revisionSlides) {
            if(revisionSlide.getSlideIndex() > getRevisionSlideRequest.getSlideIndex()) {
                revisionSlide.setSlideIndex(revisionSlide.getSlideIndex() + 1);
                slideRepository.saveAndFlush(revisionSlide);
            }
        }

        RevisionSlide revisionSlide = new RevisionSlide(getRevisionSlideRequest.getSlideIndex() + 1, bookTopic, u, RevisionSlideType.RevisionSlide);
        revisionSlide.setComment("Inserted after lesson creation");
        slideRepository.saveAndFlush(revisionSlide);

        bookTopic.incrementVersion();
        bookTopicRepository.save(bookTopic);
        bookTopic.getBookChapter().incrementVersion();
        bookChapterRepository.save(bookTopic.getBookChapter());

        return new ResponseEntity(new ApiResponse(true, "Deleted Successfully"), HttpStatus.OK);
    }

    private void createSlideCopy(RevisionSlide slide, User user) {
        if(slide != null) {
            RevisionSlide newSlide = new RevisionSlide(slide, user);
            slideRepository.saveAndFlush(newSlide);

            if(slide.getType() == RevisionSlideType.RevisionSlide) {
                ArrayList<RevisionPoint> rps = (ArrayList<RevisionPoint>) revisionPointRepository.findByRevisionSlide(slide);
                for (RevisionPoint revisionPoint : rps) {
                    if (revisionPoint.getStatus() != ContentStatus.DELETED) {
                        RevisionPoint rp = new RevisionPoint(revisionPoint, newSlide);
                        revisionPointRepository.saveAndFlush(rp);
                    }
                }

                ArrayList<RevealInteraction> ris = (ArrayList<RevealInteraction>) revealInteractionRepository.findByRevisionSlide(slide);
                for (RevealInteraction interaction : ris) {
                    if (interaction.getStatus() != ContentStatus.DELETED) {
                        RevealInteraction ri = new RevealInteraction(interaction, newSlide);
                        revealInteractionRepository.saveAndFlush(ri);
                    }
                }

                ArrayList<ConfirmInteraction> cis = (ArrayList<ConfirmInteraction>) confirmInteractionRepository.findByRevisionSlide(slide);
                for (ConfirmInteraction interaction : cis) {
                    if (interaction.getStatus() != ContentStatus.DELETED) {
                        ConfirmInteraction ci = new ConfirmInteraction(interaction, newSlide);
                        confirmInteractionRepository.saveAndFlush(ci);
                    }
                }

                ArrayList<Diagram> digs = (ArrayList<Diagram>) diagramRepository.findByRevisionSlide(slide);
                for (Diagram diagram : digs) {
                    if (diagram.getStatus() != ContentStatus.DELETED) {
                        Diagram d = new Diagram(diagram, newSlide);
                        diagramRepository.saveAndFlush(d);
                    }
                }
            }
            else if(slide.getType() == RevisionSlideType.QuestionSlide) {

            }
        }
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/get/allSlides")
    public List<GetRevisionSlideResponse> getAllSlides(@Valid @RequestBody GetRevisionSlideRequest getRevisionSlideRequest) {
        Long id = getRevisionSlideRequest.getTopicId();
        Integer bookTopicId = Integer.parseInt(id.toString());
        BookTopic bookTopic = bookTopicRepository.getOne(bookTopicId);

        ArrayList<GetRevisionSlideResponse> revisionSlides = new ArrayList<>();

        return revisionSlides;
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT", "ROLE_TEACHER"})
    @PostMapping(value="/get/slidesForTopic")
    public ArrayList<Slide> getSlidesForTopic(@Valid @RequestBody GetSlidesForTopicRequest request) {
        Book book = bookRepository.findByName(request.getBookName());
        BookChapter bookChapter = bookChapterRepository.findByNameAndBook(request.getBookChapterName(), book);
        BookTopic bookTopic = bookTopicRepository.findByNameAndBookChapter(request.getBookTopicName(), bookChapter);

        return getSlides(bookTopic);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR", "ROLE_STUDENT", "ROLE_TEACHER"})
    @PostMapping(value="/get/slidesForTopicIfUpdated")
    public ArrayList<Slide> getSlidesForTopicIfUpdated(@Valid @RequestBody GetSlidesForTopicRequest request) {
        Book book = bookRepository.findByName(request.getBookName());
        BookChapter bookChapter = bookChapterRepository.findByNameAndBook(request.getBookChapterName(), book);
        BookTopic bookTopic = bookTopicRepository.findByNameAndBookChapter(request.getBookTopicName(), bookChapter);

        logger.info(bookTopic.toString());

        if(bookTopic.getVersion() > request.getVersion()) {
            return getSlides(bookTopic);
        }
        else {
            return null;
        }
    }

    private ArrayList<Slide> getSlides(BookTopic bookTopic) {
        List<RevisionSlide> revisionSlides = slideRepository.findByBookTopic(bookTopic);
        ArrayList<Slide> revisionSlideResponses = new ArrayList<>();

        for(int i = 0; i < revisionSlides.size(); i++) {
            Slide responseSlide = null;
            RevisionSlide slide = revisionSlides.get(i);

            if(slide.getType() != RevisionSlideType.QuestionSlide) {
                ArrayList<RevisionPoint> rps = (ArrayList<RevisionPoint>) revisionPointRepository.findByRevisionSlide(slide);
                ArrayList<RevisionPoint> revisionPoints = new ArrayList<>();
                for (RevisionPoint revisionPoint : rps) {
                    if (revisionPoint.getStatus() != ContentStatus.DELETED)
                        revisionPoints.add(revisionPoint);
                }

                ArrayList<RevealInteraction> ris = (ArrayList<RevealInteraction>) revealInteractionRepository.findByRevisionSlide(slide);
                ArrayList<RevealInteraction> revealInteractions = new ArrayList<>();
                for (RevealInteraction interaction : ris) {
                    if (interaction.getStatus() != ContentStatus.DELETED)
                        revealInteractions.add(interaction);
                }

                ArrayList<ConfirmInteraction> cis = (ArrayList<ConfirmInteraction>) confirmInteractionRepository.findByRevisionSlide(slide);
                ArrayList<ConfirmInteraction> confirmInteractions = new ArrayList<>();
                for (ConfirmInteraction interaction : cis) {
                    if (interaction.getStatus() != ContentStatus.DELETED)
                        confirmInteractions.add(interaction);
                }

                ArrayList<Diagram> digs = (ArrayList<Diagram>) diagramRepository.findByRevisionSlide(slide);
                ArrayList<Diagram> diagrams = new ArrayList<>();
                for (Diagram diagram : digs) {
                    if (diagram.getStatus() != ContentStatus.DELETED)
                        diagrams.add(diagram);
                }

                RevealInteraction revealInteraction = null;
                if (revealInteractions.size() == 1) {
                    revealInteraction = revealInteractions.get(0);
                }

                ConfirmInteraction confirmInteraction = null;
                if (confirmInteractions.size() == 1) {
                    confirmInteraction = confirmInteractions.get(0);
                }

                Diagram diagram = null;
                if (diagrams.size() == 1) {
                    diagram = diagrams.get(0);
                }

                responseSlide = new Slide();

                if (revisionPoints.size() == 2) {
                    responseSlide.setRevisionPoint1(new RP1(revisionPoints.get(0).getPreface(), revisionPoints.get(0).getExplanation()));
                    responseSlide.setRevisionPoint2(new RP2(revisionPoints.get(1).getPreface(), revisionPoints.get(1).getExplanation()));
                }
                else if (revisionPoints.size() == 1) {
                    responseSlide.setRevisionPoint1(new RP1(revisionPoints.get(0).getPreface(), revisionPoints.get(0).getExplanation()));

                    if (revealInteraction != null) {
                        responseSlide.setRevealInteraction(new RI(revealInteraction.getQuestion(), revealInteraction.getAnswer()));
                    }
                    else if (confirmInteraction != null) {
                        responseSlide.setConfirmInteraction(new CI(confirmInteraction.getQuestion(), confirmInteraction.getAffirmativeAnswer(), confirmInteraction.getNegativeAnswer()));
                    }
                    else if (diagram != null) {
                        responseSlide.setDiagram(new DIG(diagram.getCaption(), diagram.getUrl()));
                    }
                }
                else if (revisionPoints.size() == 0) {
                    if (revealInteraction != null) {
                        responseSlide.setRevealInteraction(new RI(revealInteraction.getQuestion(), revealInteraction.getAnswer()));
                    }
                    else if (confirmInteraction != null) {
                        responseSlide.setConfirmInteraction(new CI(confirmInteraction.getQuestion(), confirmInteraction.getAffirmativeAnswer(), confirmInteraction.getNegativeAnswer()));
                    }
                    else if (diagram != null) {
                        responseSlide.setDiagram(new DIG(diagram.getCaption(), diagram.getUrl()));
                    }

                    logger.info("Has 0 Revision Point");
                }

                responseSlide.setSlideIndex(slide.getSlideIndex());
                responseSlide.setSlideType("Revision");
                revisionSlideResponses.add(responseSlide);
            }
            else if(slide.getType() == RevisionSlideType.QuestionSlide) {
                responseSlide = new Slide();

                ArrayList<Question> qs = (ArrayList<Question>) questionRepository.findByRevisionSlide(slide);
                Question question = null;

                for (Question q : qs) {
                    if(q.getStatus() != ContentStatus.DELETED) {
                        question = q;
                        break;
                    }
                }

                responseSlide.setQuestion(question);
                responseSlide.setSlideIndex(slide.getSlideIndex());
                responseSlide.setSlideType("Question");

                revisionSlideResponses.add(responseSlide);
            }
        }

        return revisionSlideResponses;
    }
}