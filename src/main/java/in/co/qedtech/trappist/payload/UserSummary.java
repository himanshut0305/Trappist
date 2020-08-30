package in.co.qedtech.trappist.payload;

import java.util.Collection;

public class UserSummary {
    private Long id;
    private String username;
    private String name;
    private String email;
    private Collection authorities;

    public UserSummary(Long id, String username, String name, String email, Collection authorities) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserSummary{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
