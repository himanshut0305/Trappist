package in.co.qedtech.trappist.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SaveSubjectGroupRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SaveSubjectGroupRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
