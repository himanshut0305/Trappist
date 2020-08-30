package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotNull;

public class GetSubjectsRequest {
    @NotNull
    private int subjectGroupId;

    public long getSubjectGroupId() {
        return subjectGroupId;
    }

    public void setSubjectGroupId(int subjectGroupId) {
        this.subjectGroupId = subjectGroupId;
    }

    @Override
    public String toString() {
        return "GetSubjectsRequest{" +
                "subjectGroupId=" + subjectGroupId +
                '}';
    }
}
