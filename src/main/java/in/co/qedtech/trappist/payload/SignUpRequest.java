package in.co.qedtech.trappist.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import in.co.qedtech.trappist.model.RoleName;

public class SignUpRequest {
    @NotBlank
    @Size(min = 4, max = 40)
    private String name;

    @NotBlank
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    private String role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleName getRole() {
        if(role.equals("ROLE_SUPER_ADMIN")) {
            return RoleName.ROLE_SCHOOL_ADMIN;
        }
        else if(role.equals("ROLE_SUBJECT_EXPERT")) {
            return RoleName.ROLE_SUBJECT_EXPERT;
        }
        else if(role.equals("ROLE_CONTRIBUTOR")) {
            return RoleName.ROLE_CONTRIBUTOR;
        }
        else if(role.equals("ROLE_SCHOOL_ADMIN")) {
            return RoleName.ROLE_SCHOOL_ADMIN;
        }
        else if(role.equals("ROLE_TEACHER")) {
            return RoleName.ROLE_TEACHER;
        }
        else if(role.equals("ROLE_STUDENT")) {
            return RoleName.ROLE_STUDENT;
        }
        else {
            return RoleName.ROLE_USER;
        }
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "SignUpRequest{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
