package in.co.qedtech.trappist.payload;

import java.util.ArrayList;

public class SaveSchoolClassRequest {
    private int yearId;
    private int schoolId;
    private String fromSection;
    private String toSection;

    public SaveSchoolClassRequest(int yearId, int schoolId, String fromSection, String toSection) {
        this.yearId = yearId;
        this.schoolId = schoolId;
        this.fromSection = fromSection;
        this.toSection = toSection;
    }

    public int getYearId() {
        return yearId;
    }

    public void setYearId(int yearId) {
        this.yearId = yearId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getFromSection() {
        return fromSection;
    }

    public void setFromSection(String fromSection) {
        this.fromSection = fromSection;
    }

    public String getToSection() {
        return toSection;
    }

    public void setToSection(String toSection) {
        this.toSection = toSection;
    }

    @Override
    public String toString() {
        return "SaveSchoolClassRequest{" +
                "yearId=" + yearId +
                ", schoolId=" + schoolId +
                ", fromSection='" + fromSection + '\'' +
                ", toSection='" + toSection + '\'' +
                '}';
    }
}