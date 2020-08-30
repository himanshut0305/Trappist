package in.co.qedtech.trappist.payload;

public class GetBookTopicsRequest {
    private Long bookChapterId;

    public Long getBookChapterId() {
        return bookChapterId;
    }

    public void setBookChapterId(Long bookChapterId) {
        this.bookChapterId = bookChapterId;
    }

    @Override
    public String toString() {
        return "GetBookTopicsRequest{" +
                "bookChapterId=" + bookChapterId +
                '}';
    }
}
