package in.co.qedtech.trappist.payload;

public class GetSchoolByCodeRequest {
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "GetSchoolByCodeRequest{" +
                "code='" + code + '\'' +
                '}';
    }
}
