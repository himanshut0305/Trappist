package in.co.qedtech.trappist.slideResponse;

import in.co.qedtech.trappist.model.Question;

public class Slide {
    private int slideIndex;
    private String slideType;

    private RP1 revisionPoint1;
    private RP2 revisionPoint2;
    private RI revealInteraction;
    private CI confirmInteraction;
    private DIG diagram;

    private Question question;

    public int getSlideIndex() {
        return slideIndex;
    }

    public void setSlideIndex(int slideIndex) {
        this.slideIndex = slideIndex;
    }

    public String getSlideType() {
        return slideType;
    }

    public void setSlideType(String slideType) {
        this.slideType = slideType;
    }

    public RP1 getRevisionPoint1() {
        return revisionPoint1;
    }

    public void setRevisionPoint1(RP1 revisionPoint1) {
        this.revisionPoint1 = revisionPoint1;
    }

    public RP2 getRevisionPoint2() {
        return revisionPoint2;
    }

    public void setRevisionPoint2(RP2 revisionPoint2) {
        this.revisionPoint2 = revisionPoint2;
    }

    public RI getRevealInteraction() {
        return revealInteraction;
    }

    public void setRevealInteraction(RI revealInteraction) {
        this.revealInteraction = revealInteraction;
    }

    public CI getConfirmInteraction() {
        return confirmInteraction;
    }

    public void setConfirmInteraction(CI confirmInteraction) {
        this.confirmInteraction = confirmInteraction;
    }

    public DIG getDiagram() {
        return diagram;
    }

    public void setDiagram(DIG diagram) {
        this.diagram = diagram;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Slide{" +
                "slideIndex=" + slideIndex +
                ", slideType='" + slideType + '\'' +
                ", revisionPoint1=" + revisionPoint1 +
                ", revisionPoint2=" + revisionPoint2 +
                ", revealInteraction=" + revealInteraction +
                ", confirmInteraction=" + confirmInteraction +
                ", diagram=" + diagram +
                ", question=" + question +
                '}';
    }
}
