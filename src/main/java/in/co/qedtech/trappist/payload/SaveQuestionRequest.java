package in.co.qedtech.trappist.payload;

import in.co.qedtech.trappist.model.QuestionCategory;
import in.co.qedtech.trappist.model.QuestionType;

import java.util.List;

public class SaveQuestionRequest {
    private String question;
    private List<OptionRequest> option;
    private String comment;

    private Long topicId;
    private String questionType;
    private String instruction;
    private String diagram;

    private int level;
    private String category;
    private Integer currentQuestionId;

    public SaveQuestionRequest() { }
    public SaveQuestionRequest(String question, List<OptionRequest> option, String comment, Long topicId, String questionType, String instruction, int level, String category) {
        this.question = question;
        this.option = option;
        this.comment = comment;
        this.topicId = topicId;
        this.questionType = questionType;
        this.instruction = instruction;
        this.level = level;
        this.category = category;
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

    public QuestionCategory getCategory() {
        if(category.equals("CONCEPTUAL")) {
            return QuestionCategory.CONCEPTUAL;
        }
        else if(category.equals("NUMERICAL")) {
            return QuestionCategory.NUMERICAL;
        }
        else if(category.equals("MEMORY_BASED")) {
            return QuestionCategory.MEMORY_BASED;
        }
        else {
            return QuestionCategory.UNDETERMINED;
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiagram() {
        return diagram;
    }

    public void setDiagram(String diagram) {
        this.diagram = diagram;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Integer getCurrentQuestionId() {
        return currentQuestionId;
    }

    public void setCurrentQuestionId(Integer currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
    }

    @Override
    public String toString() {
        return "SaveQuestionRequest{" +
                "question='" + question + '\'' +
                ", option=" + option +
                ", comment='" + comment + '\'' +
                ", topicId=" + topicId +
                ", questionType='" + questionType + '\'' +
                ", instruction='" + instruction + '\'' +
                ", diagram='" + diagram + '\'' +
                ", level=" + level +
                ", currentQuestionId=" + currentQuestionId +
                '}';
    }
}
