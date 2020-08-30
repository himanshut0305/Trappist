package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SaveChapterRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotNull
    private int subjectId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectGroupId) {
        this.subjectId = subjectGroupId;
    }
}
