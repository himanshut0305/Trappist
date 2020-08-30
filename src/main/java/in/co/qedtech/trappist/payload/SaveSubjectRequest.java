package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SaveSubjectRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotNull
    private int subjectGroupId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSubjectGroupId() {
        return subjectGroupId;
    }

    public void setSubjectGroupId(int subjectGroupId) {
        this.subjectGroupId = subjectGroupId;
    }
}
