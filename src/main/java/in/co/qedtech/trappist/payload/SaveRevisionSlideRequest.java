package in.co.qedtech.trappist.payload;

public class SaveRevisionSlideRequest {
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
    private String diagram;

    private String comment;

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
        if(preface1 == null)
            return "";

        return preface1;
    }

    public void setPreface1(String preface1) {
        this.preface1 = preface1;
    }

    public String getExplanation1() {
        if(explanation1 == null)
            return "";

        return explanation1;
    }

    public void setExplanation1(String explanation1) {
        this.explanation1 = explanation1;
    }

    public String getPreface2() {
        if(preface2 == null)
            return "";

        return preface2;
    }

    public void setPreface2(String preface2) {
        this.preface2 = preface2;
    }

    public String getExplanation2() {
        if(explanation2 == null)
            return "";

        return explanation2;
    }

    public void setExplanation2(String explanation2) {
        this.explanation2 = explanation2;
    }

    public String getRiQuestion() {
        if(riQuestion == null)
            return "";

        return riQuestion;
    }

    public void setRiQuestion(String riQuestion) {
        this.riQuestion = riQuestion;
    }

    public String getRiAnswer() {
        if(riAnswer == null)
            return "";

        return riAnswer;
    }

    public void setRiAnswer(String riAnswer) {
        this.riAnswer = riAnswer;
    }

    public String getCiQuestion() {
        if(ciQuestion == null)
            return "";

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
        if(diagramCaption == null)
            return  "";

        return diagramCaption;
    }

    public void setDiagramCaption(String diagramCaption) {
        this.diagramCaption = diagramCaption;
    }

    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "SaveRevisionSlideRequest{" +
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
                ", diagram='" + diagram + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}