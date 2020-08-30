package in.co.qedtech.trappist.payload;

public class GetChaptersByBookNameRequest {
    String bookName;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String toString() {
        return "GetChaptersByBookNameRequest{" +
                "bookName='" + bookName + '\'' +
                '}';
    }
}
