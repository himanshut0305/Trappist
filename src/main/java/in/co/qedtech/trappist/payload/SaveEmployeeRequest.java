package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotBlank;

public class SaveEmployeeRequest {
    String ename;
    String eid;
    int esalary;

    public SaveEmployeeRequest(String ename, String eid, int esalary) {
        this.ename = ename;
        this.eid = eid;
        this.esalary = esalary;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public int getEsalary() {
        return esalary;
    }

    public void setEsalary(int esalary) {
        this.esalary = esalary;
    }

    @Override
    public String toString() {
        return "SaveEmployeeRequest{" +
                "ename='" + ename + '\'' +
                ", eid='" + eid + '\'' +
                ", esalary=" + esalary +
                '}';
    }
}
