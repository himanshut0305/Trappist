package in.co.qedtech.trappist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import in.co.qedtech.trappist.model.audit.UserDateAudit;

@Entity
@Table(name = "options")
public class Option extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String optionText;

    private String matchingOption;
    private boolean isCorrect;

    @Column(columnDefinition="text")
    private String explanation;

    public Option() { }

    public Option(Option option) {
        this.optionText = option.getOptionText();
        this.matchingOption = option.getMatchingOption();
        this.isCorrect = option.isCorrect();
        this.explanation = option.getExplanation();
    }

    public Option(@NotBlank String optionText, boolean isCorrect, String explanation) {
        this.optionText = optionText;
        this.isCorrect = isCorrect;
        this.explanation = explanation;
    }

    public Option(@NotBlank String optionText, String matchingOption, boolean isCorrect, String explanation) {
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
        return "Option{" +
                "id=" + id +
                ", optionText='" + optionText + '\'' +
                ", matchingOption='" + matchingOption + '\'' +
                ", isCorrect=" + isCorrect +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}