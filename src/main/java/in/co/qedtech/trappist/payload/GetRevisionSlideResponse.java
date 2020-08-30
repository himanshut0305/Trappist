package in.co.qedtech.trappist.payload;

import in.co.qedtech.trappist.model.ConfirmInteraction;
import in.co.qedtech.trappist.model.Diagram;
import in.co.qedtech.trappist.model.RevealInteraction;
import in.co.qedtech.trappist.model.RevisionPoint;
import in.co.qedtech.trappist.model.RevisionSlideType;

public class GetRevisionSlideResponse {
    private Long topicId;
    private Integer slideIndex;

    private String preface1;
    private String explanation1;
    private String preface2;
    private String explanation2;
    private String riQuestion;
    private String riAnswer;
    private String ciQuestion;
    private String ciAffirmativeAnswer;
    private String ciNegativeAnswer;
    private String diagramCaption;
    private String diagramURL;

    private String createdBy;
    private String reviewedBy;
    private String comment;
    private String slideType;
    private boolean isLastSlide;
    private long numberOfSlides;

    private Integer slideId;

    public GetRevisionSlideResponse() {}

    public GetRevisionSlideResponse(RevisionPoint rp1) {
        this.preface1 = rp1.getPreface();
        this.explanation1 = rp1.getExplanation();
    }

    public GetRevisionSlideResponse(RevisionPoint rp1, RevisionPoint rp2) {
        this.preface1 = rp1.getPreface();
        this.explanation1 = rp1.getExplanation();
        this.preface2 = rp2.getPreface();
        this.explanation2 = rp2.getExplanation();
    }

    public GetRevisionSlideResponse(RevisionPoint rp1, RevealInteraction ri) {
        this.preface1 = rp1.getPreface();
        this.explanation1 = rp1.getExplanation();
        this.riQuestion = ri.getQuestion();
        this.riAnswer = ri.getAnswer();
    }

    public GetRevisionSlideResponse(RevisionPoint rp1, ConfirmInteraction ci) {
        this.preface1 = rp1.getPreface();
        this.explanation1 = rp1.getExplanation();
        this.ciQuestion = ci.getQuestion();
        this.ciAffirmativeAnswer = ci.getAffirmativeAnswer();
        this.ciNegativeAnswer = ci.getNegativeAnswer();
    }

    public GetRevisionSlideResponse(RevisionPoint rp1, Diagram d) {
        this.preface1 = rp1.getPreface();
        this.explanation1 = rp1.getExplanation();
        this.diagramCaption = d.getCaption();
        this.diagramURL = d.getUrl();
    }

    public GetRevisionSlideResponse(RevealInteraction ri) {
        this.riQuestion = ri.getQuestion();
        this.riAnswer = ri.getAnswer();
    }

    public GetRevisionSlideResponse(ConfirmInteraction ci) {
        this.ciQuestion = ci.getQuestion();
        this.ciAffirmativeAnswer = ci.getAffirmativeAnswer();
        this.ciNegativeAnswer = ci.getNegativeAnswer();
    }

    public GetRevisionSlideResponse(Diagram d) {
        this.diagramCaption = d.getCaption();
        this.diagramURL = d.getUrl();
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

    public String getPreface1() {
        return preface1;
    }

    public void setPreface1(String preface1) {
        this.preface1 = preface1;
    }

    public String getExplanation1() {
        return explanation1;
    }

    public void setExplanation1(String explanation1) {
        this.explanation1 = explanation1;
    }

    public String getPreface2() {
        return preface2;
    }

    public void setPreface2(String preface2) {
        this.preface2 = preface2;
    }

    public String getExplanation2() {
        return explanation2;
    }

    public void setExplanation2(String explanation2) {
        this.explanation2 = explanation2;
    }

    public String getRiQuestion() {
        return riQuestion;
    }

    public void setRiQuestion(String riQuestion) {
        this.riQuestion = riQuestion;
    }

    public String getRiAnswer() {
        return riAnswer;
    }

    public void setRiAnswer(String riAnswer) {
        this.riAnswer = riAnswer;
    }

    public String getCiQuestion() {
        return ciQuestion;
    }

    public void setCiQuestion(String ciQuestion) {
        this.ciQuestion = ciQuestion;
    }

    public String getCiAffirmativeAnswer() {
        return ciAffirmativeAnswer;
    }

    public void setCiAffirmativeAnswer(String ciAffirmativeAnswer) {
        this.ciAffirmativeAnswer = ciAffirmativeAnswer;
    }

    public String getCiNegativeAnswer() {
        return ciNegativeAnswer;
    }

    public void setCiNegativeAnswer(String ciNegativeAnswer) {
        this.ciNegativeAnswer = ciNegativeAnswer;
    }

    public String getDiagramCaption() {
        return diagramCaption;
    }

    public void setDiagramCaption(String diagramCaption) {
        this.diagramCaption = diagramCaption;
    }

    public String getDiagramURL() {
        return diagramURL;
    }

    public void setDiagramURL(String diagramURL) {
        this.diagramURL = diagramURL;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSlideType() {
        return slideType;
    }

    public void setSlideType(RevisionSlideType slideType) {
        if(slideType == RevisionSlideType.RevisionSlide) {
            this.slideType = "Revision";
        }
        else if(slideType == RevisionSlideType.QuestionSlide) {
            this.slideType = "Question";
        }
    }

    public boolean isLastSlide() {
        return isLastSlide;
    }

    public void setLastSlide(boolean lastSlide) {
        isLastSlide = lastSlide;
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
        return "GetRevisionSlideResponse{" +
                "topicId=" + topicId +
                ", slideIndex=" + slideIndex +
                ", preface1='" + preface1 + '\'' +
                ", explanation1='" + explanation1 + '\'' +
                ", preface2='" + preface2 + '\'' +
                ", explanation2='" + explanation2 + '\'' +
                ", riQuestion='" + riQuestion + '\'' +
                ", riAnswer='" + riAnswer + '\'' +
                ", ciQuestion='" + ciQuestion + '\'' +
                ", ciAffirmativeAnswer='" + ciAffirmativeAnswer + '\'' +
                ", ciNegativeAnswer='" + ciNegativeAnswer + '\'' +
                ", diagramCaption='" + diagramCaption + '\'' +
                ", diagramURL='" + diagramURL + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", reviewedBy='" + reviewedBy + '\'' +
                ", comment='" + comment + '\'' +
                ", slideType='" + slideType + '\'' +
                ", isLastSlide=" + isLastSlide +
                ", numberOfSlides=" + numberOfSlides +
                ", slideId=" + slideId +
                '}';
    }
}