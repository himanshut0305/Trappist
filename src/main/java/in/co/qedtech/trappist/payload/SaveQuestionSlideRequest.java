package in.co.qedtech.trappist.payload;

import java.util.List;

import in.co.qedtech.trappist.model.QuestionType;

public class SaveQuestionSlideRequest {
    private String question;
    private List<OptionRequest> option;
    private String comment;

    private Long topicId;
    private Integer slideIndex;
    private String questionType;
    private String instruction;
    private String diagram;

    private Boolean isInteractive;

    public SaveQuestionSlideRequest() { }
    public SaveQuestionSlideRequest(String question, List<OptionRequest> option, String comment, Long topicId, Integer slideIndex, String questionType, String instruction) {
        this.question = question;
        this.option = option;
        this.comment = comment;
        this.topicId = topicId;
        this.slideIndex = slideIndex;
        this.questionType = questionType;
        this.instruction = instruction;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<OptionRequest> getOption() {
        return option;
    }

    public void setOption(List<OptionRequest> option) {
        this.option = option;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public QuestionType getType() {
        if (questionType.equals("MCQ_Single_Answer")) {
            return QuestionType.MCQ_Single_Answer;
        }
        else if (questionType.equals("MCQ_Multiple_Answer")) {
            return QuestionType.MCQ_Multiple_Answer;
        }
        else if (questionType.equals("MCQ_True_False")) {
            return QuestionType.MCQ_True_False;
        }
        else if (questionType.equals("MCQ_One_Word_Answer")) {
            return QuestionType.One_Word_Answer;
        }
        else if (questionType.equals("MCQ_Jumbled_Words")) {
            return QuestionType.Jumbled_Words;
        }
        else if (questionType.equals("MCQ_Match_The_Columns")) {
            return QuestionType.Match_The_Columns;
        }
        else {
            return QuestionType.Undetermined;
        }
    }

    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public boolean isInteractive() {
        if(isInteractive == null)
            return false;

        return isInteractive;
    }

    public void setInteractive(boolean interactive) {
        isInteractive = interactive;
    }
}
