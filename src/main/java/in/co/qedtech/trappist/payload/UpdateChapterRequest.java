package in.co.qedtech.trappist.payload;

public class UpdateChapterRequest {
    private String chapterName;
    private int chapterId;

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    @Override
    public String toString() {
        return "UpdateChapterRequest{" +
                "chapterName='" + chapterName + '\'' +
                ", chapterId=" + chapterId +
                '}';
    }
}
