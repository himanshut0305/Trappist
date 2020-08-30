package in.co.qedtech.trappist.payload;

public class UpdateBookChapterRequest {
    int bookChapterId;
    String bookChapterName;
    int bookChapterIndex;

    public int getBookChapterId() {
        return bookChapterId;
    }

    public void setBookChapterId(int bookChapterId) {
        this.bookChapterId = bookChapterId;
    }

    public String getBookChapterName() {
        return bookChapterName;
    }

    public void setBookChapterName(String bookChapterName) {
        this.bookChapterName = bookChapterName;
    }

    public int getBookChapterIndex() {
        return bookChapterIndex;
    }

    public void setBookChapterIndex(int bookChapterIndex) {
        this.bookChapterIndex = bookChapterIndex;
    }
}
