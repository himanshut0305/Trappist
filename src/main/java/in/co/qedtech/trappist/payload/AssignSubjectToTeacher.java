package in.co.qedtech.trappist.payload;

public class AssignSubjectToTeacher {
    int teacherId;
    int schoolClassId;
    int subjectId;

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getSchoolClassId() {
        return schoolClassId;
    }

    public void setSchoolClassId(int schoolClassId) {
        this.schoolClassId = schoolClassId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "AssignSubjectToTeacher{" +
                "teacherId=" + teacherId +
                ", schoolClassId=" + schoolClassId +
                ", subjectId=" + subjectId +
                '}';
    }
}
