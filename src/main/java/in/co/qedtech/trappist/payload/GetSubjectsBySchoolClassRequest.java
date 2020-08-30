package in.co.qedtech.trappist.payload;

public class GetSubjectsBySchoolClassRequest {
    int schoolClassId;

    public int getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(int schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    @Override
    public String toString() {
        return "GetSubjectsBySchoolClassRequest{" +
                "schoolClassId=" + schoolClassId +
                '}';
    }
}
