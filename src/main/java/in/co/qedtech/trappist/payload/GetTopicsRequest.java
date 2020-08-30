package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotNull;

public class GetTopicsRequest {
    @NotNull
    private int chapterId;

    public long getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }
}
