package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotNull;

public class GetBookChaptersRequest {
    @NotNull
    private Long bookId;

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "GetBookChaptersRequest{" +
                "bookId=" + bookId +
                '}';
    }
}
