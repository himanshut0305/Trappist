package in.co.qedtech.trappist.payload;

public class GetSchoolYearRequest {
    private int SchoolId;
    public GetSchoolYearRequest(){}
    public GetSchoolYearRequest(int schoolId) {
        SchoolId = schoolId;
    }
    public int getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(int schoolId) {
        SchoolId = schoolId;
    }

    @Override
    public String toString() {
        return "GetSchoolYearRequest{" +
                "SchoolId=" + SchoolId +
                '}';
    }
}
