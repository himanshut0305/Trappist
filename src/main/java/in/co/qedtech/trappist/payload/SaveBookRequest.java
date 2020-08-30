package in.co.qedtech.trappist.payload;

public class SaveBookRequest {
    private String name;
    private int subjectId;
    private int schoolYearId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return "SaveBookRequest{" +
                "name='" + name + '\'' +
                ", subjectId=" + subjectId +
                ", schoolYearId=" + schoolYearId +
                '}';
    }
}