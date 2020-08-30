package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotNull;

public class GetChaptersRequest {
    @NotNull
    private int subjectId;

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "GetChaptersRequest{" +
                "subjectId=" + subjectId +
                '}';
    }
}
