package in.co.qedtech.trappist.payload;

import java.util.Date;

public class GetSlidesForTopicRequest {
    private String bookName;
    private String bookChapterName;
    private String bookTopicName;
    private int version;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookChapterName() {
        return bookChapterName;
    }

    public void setBookChapterName(String bookChapterName) {
        this.bookChapterName = bookChapterName;
    }

    public String getBookTopicName() {
        return bookTopicName;
    }

    public void setBookTopicName(String bookTopicName) {
        this.bookTopicName = bookTopicName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "GetSlidesForTopicRequest{" +
                "bookName='" + bookName + '\'' +
                ", bookChapterName='" + bookChapterName + '\'' +
                ", bookTopicName='" + bookTopicName + '\'' +
                ", version=" + version +
                '}';
    }
}
