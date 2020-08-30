package in.co.qedtech.trappist.payload;

import java.util.List;

public class LinkBookTopicToTopicRequest {
    private Integer bookTopicId;
    private Integer topicId;

    public Integer getBookTopicId() {
        return bookTopicId;
    }

    public void setBookTopicId(Integer bookTopicId) {
        this.bookTopicId = bookTopicId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    @Override
    public String toString() {
        return "LinkBookTopicToTopicRequest{" +
                "bookTopicId=" + bookTopicId +
                ", topicId=" + topicId +
                '}';
    }
}
