package in.co.qedtech.trappist.slideResponse;

public class RP1 {
    private String preface;
    private String explanation;

    public RP1(String preface, String explanation) {
        this.preface = preface;
        this.explanation = explanation;
    }

    public String getPreface() {
        return preface;
    }

    public void setPreface(String preface) {
        this.preface = preface;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "rp1{" +
                "preface='" + preface + '\'' +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
