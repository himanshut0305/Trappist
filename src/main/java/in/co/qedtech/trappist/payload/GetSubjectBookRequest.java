package in.co.qedtech.trappist.payload;

public class GetSubjectBookRequest {
    private int schoolYearId;
    private int subjectId;
    private int bookId;

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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "GetSubjectBookRequest{" +
                "schoolYearId=" + schoolYearId +
                ", subjectId=" + subjectId +
                ", bookId=" + bookId +
                '}';
    }
}
