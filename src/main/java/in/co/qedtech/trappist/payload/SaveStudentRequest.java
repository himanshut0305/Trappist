package in.co.qedtech.trappist.payload;

public class SaveStudentRequest {
    private String name;
    private String rollNo;
    private String admissionNo;
    private String email;
    private int schoolClassId;

    public SaveStudentRequest(String name, String rollNo, String admissionNo, String email, int schoolClassId) {
        this.name = name;
        this.rollNo = rollNo;
        this.admissionNo = admissionNo;
        this.email = email;
        this.schoolClassId = schoolClassId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(int schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    @Override
    public String toString() {
        return "SaveStudentRequest{" +
                ", name='" + name + '\'' +
                ", rollNo='" + rollNo + '\'' +
                ", admissionNo='" + admissionNo + '\'' +
                ", email='" + email + '\'' +
                ", schoolClassId=" + schoolClassId +
                '}';
    }
}