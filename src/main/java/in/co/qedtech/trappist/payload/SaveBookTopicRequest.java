package in.co.qedtech.trappist.payload;

public class SaveBookTopicRequest {
    private String name;
    private Long bookChapterId;
    private int topicIndex;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBookChapterId() {
        return bookChapterId;
    }

    public void setBookChapterId(Long bookChapterId) {
        this.bookChapterId = bookChapterId;
    }

    public int getTopicIndex() {
        return topicIndex;
    }

    public void setTopicIndex(int topicIndex) {
        this.topicIndex = topicIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SaveBookTopicRequest{" +
                "name='" + name + '\'' +
                ", bookChapterId=" + bookChapterId +
                ", topicIndex=" + topicIndex +
                ", description='" + description + '\'' +
                '}';
    }
}