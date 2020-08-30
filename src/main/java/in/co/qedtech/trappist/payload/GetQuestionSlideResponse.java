package in.co.qedtech.trappist.payload;

import java.util.Set;
import in.co.qedtech.trappist.model.Option;

public class GetQuestionSlideResponse {
    private Long topicId;
    private Integer slideIndex;

    private String question;
    private Set<Option> option;

    private String questionType;
    private String instruction;
    private String diagramURL;
    private boolean isAllSelectEnabled;

    private String createdBy;
    private String reviewedBy;
    private String comment;
    private Integer slideId;

    private long numberOfSlides;

    public GetQuestionSlideResponse() { }
    public GetQuestionSlideResponse(Long topicId, Integer slideIndex, String question, Set<Option> option, String comment, String questionType, String instruction) {
        this.topicId = topicId;
        this.slideIndex = slideIndex;
        this.question = question;
        this.option = option;
        this.comment = comment;
        this.questionType = questionType;
        this.instruction = instruction;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getSlideIndex() {
        return slideIndex;
    }

    public void setSlideIndex(Integer slideIndex) {
        this.slideIndex = slideIndex;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<Option> getOption() {
        return option;
    }

    public void setOption(Set<Option> option) {
        this.option = option;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getDiagramURL() {
        return diagramURL;
    }

    public void setDiagramURL(String diagramURL) {
        this.diagramURL = diagramURL;
    }

    public boolean isAllSelectEnabled() {
        return isAllSelectEnabled;
    }

    public void setAllSelectEnabled(boolean allSelectEnabled) {
        isAllSelectEnabled = allSelectEnabled;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public Integer getSlideId() {
        return slideId;
    }

    public void setSlideId(Integer slideId) {
        this.slideId = slideId;
    }

    public long getNumberOfSlides() {
        return numberOfSlides;
    }

    public void setNumberOfSlides(long numberOfSlides) {
        this.numberOfSlides = numberOfSlides;
    }

    @Override
    public String toString() {
        return "GetQuestionSlideResponse{" +
                "topicId=" + topicId +
                ", slideIndex=" + slideIndex +
                ", question='" + question + '\'' +
                ", option=" + option +
                ", questionType='" + questionType + '\'' +
                ", instruction='" + instruction + '\'' +
                ", diagramURL='" + diagramURL + '\'' +
                ", isAllSelectEnabled=" + isAllSelectEnabled +
                ", createdBy='" + createdBy + '\'' +
                ", reviewedBy='" + reviewedBy + '\'' +
                ", comment='" + comment + '\'' +
                ", slideId=" + slideId +
                ", numberOfSlides=" + numberOfSlides +
                '}';
    }
}