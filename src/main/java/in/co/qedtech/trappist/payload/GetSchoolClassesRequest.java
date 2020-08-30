package in.co.qedtech.trappist.payload;

public class GetSchoolClassesRequest {
    private int schoolId;

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "GetSchoolClassesRequest{" +
                "schoolId=" + schoolId +
                '}';
    }
}
