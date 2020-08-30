package in.co.qedtech.trappist.payload;

public class GetRevisionSlideRequest {
    private Long topicId;
    private Integer slideIndex;

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Integer getSlideIndex() {
        return slideIndex;
    }

    public void setSlideIndex(Integer slideIndex) {
        this.slideIndex = slideIndex;
    }

    @Override
    public String toString() {
        return "GetRevisionSlideRequest{" +
                "topicId=" + topicId +
                ", slideIndex=" + slideIndex +
                '}';
    }
}
