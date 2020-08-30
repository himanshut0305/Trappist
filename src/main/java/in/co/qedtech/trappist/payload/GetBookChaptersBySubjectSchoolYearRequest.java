package in.co.qedtech.trappist.payload;

public class GetBookChaptersBySubjectSchoolYearRequest {
    private String subjectName;
    private String schoolCode;
    private int year;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "GetBookChaptersBySubjectSchoolYearRequest{" +
                "subjectName='" + subjectName + '\'' +
                ", schoolCode='" + schoolCode + '\'' +
                ", year=" + year +
                '}';
    }
}
