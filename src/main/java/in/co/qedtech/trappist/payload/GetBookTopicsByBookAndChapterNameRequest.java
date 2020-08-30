package in.co.qedtech.trappist.payload;

public class GetBookTopicsByBookAndChapterNameRequest {
    private String bookName;
    private String chapterName;
    private int version;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "GetBookTopicsByBookAndChapterNameRequest{" +
                "bookName='" + bookName + '\'' +
                ", chapterName='" + chapterName + '\'' +
                ", version=" + version +
                '}';
    }
}
