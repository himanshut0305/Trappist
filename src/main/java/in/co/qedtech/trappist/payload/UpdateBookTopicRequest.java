package in.co.qedtech.trappist.payload;

public class UpdateBookTopicRequest {
    int bookTopicId;
    String bookTopicName;
    int bookTopicIndex;
    String description;

    public int getBookTopicId() {
        return bookTopicId;
    }

    public void setBookTopicId(int bookTopicId) {
        this.bookTopicId = bookTopicId;
    }

    public String getBookTopicName() {
        return bookTopicName;
    }

    public void setBookTopicName(String bookTopicName) {
        this.bookTopicName = bookTopicName;
    }

    public int getBookTopicIndex() {
        return bookTopicIndex;
    }

    public void setBookTopicIndex(int bookTopicIndex) {
        this.bookTopicIndex = bookTopicIndex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UpdateBookTopicRequest{" +
                "bookTopicId=" + bookTopicId +
                ", bookTopicName='" + bookTopicName + '\'' +
                ", bookTopicIndex=" + bookTopicIndex +
                ", description='" + description + '\'' +
                '}';
    }
}