package in.co.qedtech.trappist.payload;

public class SaveBookChapterRequest {
    private String name;
    private Long bookId;
    private int chapterIndex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    @Override
    public String toString() {
        return "SaveBookChapterRequest{" +
                "name='" + name + '\'' +
                ", bookId=" + bookId +
                ", chapterIndex=" + chapterIndex +
                '}';
    }
}