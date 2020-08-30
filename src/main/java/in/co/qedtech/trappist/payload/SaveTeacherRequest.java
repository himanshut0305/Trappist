package in.co.qedtech.trappist.payload;

public class SaveTeacherRequest {
    private String name;
    private String username;
    private String phoneNo;
    private String email;
    private Integer schoolId;

    public SaveTeacherRequest(String name, String username, String phoneNo, String email, Integer schoolId) {
        this.name = name;
        this.username = username;
        this.phoneNo = phoneNo;
        this.email = email;
        this.schoolId = schoolId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "SaveTeacherRequest{" +
                "name='" + name + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", email='" + email + '\'' +
                ", schoolId=" + schoolId +
                '}';
    }
}
