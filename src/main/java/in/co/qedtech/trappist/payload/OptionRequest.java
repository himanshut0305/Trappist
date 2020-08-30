package in.co.qedtech.trappist.payload;

public class OptionRequest {
    public String text;
    public boolean isCorrect;
    public String explanation;

    public OptionRequest() {
    }

    public OptionRequest(String text, boolean isCorrect, String explanation) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.explanation = explanation;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "OptionRequest{" +
                "text='" + text + '\'' +
                ", isCorrect=" + isCorrect +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}