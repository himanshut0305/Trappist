package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotNull;

public class GetBooksRequest {
    @NotNull private int subjectId;
    @NotNull private int schoolYearId;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(int schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    @Override
    public String toString() {
        return "GetBooksRequest{" +
                "subjectId=" + subjectId +
                ", schoolYearId=" + schoolYearId +
                '}';
    }
}
