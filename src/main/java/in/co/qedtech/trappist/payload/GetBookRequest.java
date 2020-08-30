package in.co.qedtech.trappist.payload;

public class GetBookRequest {
    private int subjectId;

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public GetBookRequest(int subjectId) {
        this.subjectId = subjectId;
    }
}
