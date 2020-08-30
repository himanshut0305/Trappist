package in.co.qedtech.trappist.slideResponse;

import java.util.HashSet;

public class QUES {
    private String question;
    private String questionType;
    private String instruction;
    private DIG diagram;

    private HashSet<OPTN> options;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public DIG getDiagram() {
        return diagram;
    }

    public void setDiagram(DIG diagram) {
        this.diagram = diagram;
    }

    public HashSet<OPTN> getOptions() {
        return options;
    }

    public void setOptions(HashSet<OPTN> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return "QUES{" +
                "question='" + question + '\'' +
                ", questionType='" + questionType + '\'' +
                ", instruction='" + instruction + '\'' +
                ", diagram=" + diagram +
                ", options=" + options +
                '}';
    }
}
