package in.co.qedtech.trappist.slideResponse;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class OPTN {
    private String optionText;
    private String matchingOption;
    private boolean isCorrect;
    private String explanation;

    public OPTN(String optionText, String matchingOption, boolean isCorrect, String explanation) {
        this.optionText = optionText;
        this.matchingOption = matchingOption;
        this.isCorrect = isCorrect;
        this.explanation = explanation;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public String getMatchingOption() {
        return matchingOption;
    }

    public void setMatchingOption(String matchingOption) {
        this.matchingOption = matchingOption;
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
        return "OPTN{" +
                "optionText='" + optionText + '\'' +
                ", matchingOption='" + matchingOption + '\'' +
                ", isCorrect=" + isCorrect +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
