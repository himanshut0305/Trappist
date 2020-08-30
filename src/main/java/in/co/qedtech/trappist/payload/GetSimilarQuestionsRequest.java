package in.co.qedtech.trappist.payload;

public class GetSimilarQuestionsRequest {
    int topicId;
    String question;

    public GetSimilarQuestionsRequest(int topicId, String question) {
        this.topicId = topicId;
        this.question = question;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "GetSimilarQuestionsRequest{" +
                "topicId=" + topicId +
                ", question='" + question + '\'' +
                '}';
    }
}