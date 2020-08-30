package in.co.qedtech.trappist.payload;

public class SaveSchoolYearSubjectBookRequest {
    int schoolYearId;
    int subjectId;
    int bookId;

    public SaveSchoolYearSubjectBookRequest(int schoolYearId, int subjectId, int bookId) {
        this.schoolYearId = schoolYearId;
        this.subjectId = subjectId;
        this.bookId = bookId;
    }

    public int getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(int schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "SaveSchoolYearSubjectBookRequest{" +
                "schoolYearId=" + schoolYearId +
                ", subjectId=" + subjectId +
                ", bookId=" + bookId +
                '}';
    }
}
