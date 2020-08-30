package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SaveTopicRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotNull
    private Long chapterId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getChapterId() {
        return chapterId;
    }

    public void setChapterId(Long chapterId) {
        this.chapterId = chapterId;
    }

    @Override
    public String toString() {
        return "SaveTopicRequest{" +
                "name='" + name + '\'' +
                ", chapterId=" + chapterId +
                '}';
    }
}
