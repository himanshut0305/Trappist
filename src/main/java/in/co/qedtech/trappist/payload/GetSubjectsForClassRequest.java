package in.co.qedtech.trappist.payload;

public class GetSubjectsForClassRequest {
    int schoolClassId;

    public int getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(int schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    @Override
    public String toString() {
        return "GetSubjectsForClassRequest{" +
                "schoolClassId=" + schoolClassId +
                '}';
    }
}
