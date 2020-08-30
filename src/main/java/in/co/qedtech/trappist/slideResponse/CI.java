package in.co.qedtech.trappist.slideResponse;

public class CI {
    private String question;
    private String affirmativeAnswer;
    private String negativeAnswer;

    public CI(String question, String affirmativeAnswer, String negativeAnswer) {
        this.question = question;
        this.affirmativeAnswer = affirmativeAnswer;
        this.negativeAnswer = negativeAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAffirmativeAnswer() {
        return affirmativeAnswer;
    }

    public void setAffirmativeAnswer(String affirmativeAnswer) {
        this.affirmativeAnswer = affirmativeAnswer;
    }

    public String getNegativeAnswer() {
        return negativeAnswer;
    }

    public void setNegativeAnswer(String negativeAnswer) {
        this.negativeAnswer = negativeAnswer;
    }

    @Override
    public String toString() {
        return "CI{" +
                "question='" + question + '\'' +
                ", affirmativeAnswer='" + affirmativeAnswer + '\'' +
                ", negativeAnswer='" + negativeAnswer + '\'' +
                '}';
    }
}
