package in.co.qedtech.trappist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import in.co.qedtech.trappist.exception.ResourceNotFoundException;
import in.co.qedtech.trappist.model.Subject;
import in.co.qedtech.trappist.model.User;
import in.co.qedtech.trappist.repository.UserRepository;
import in.co.qedtech.trappist.security.CurrentUser;
import in.co.qedtech.trappist.security.UserPrincipal;

import in.co.qedtech.trappist.payload.UserSummary;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("isAuthenticated()")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), currentUser.getEmail(), currentUser.getAuthorities());
        return userSummary;
    }

    @GetMapping("/user/checkUsernameAvailability")
    public Boolean checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return !userRepository.existsByUsername(username);
    }

    @GetMapping("/user/checkEmailAvailability")
    public Boolean checkEmailAvailability(@RequestParam(value = "email") String email) {
        return !userRepository.existsByEmail(email);
    }

    @GetMapping("/users/{username}")
    public UserSummary getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        UserSummary userSummary = new UserSummary(user.getId(), user.getUsername(), user.getName(), user.getEmail(), null);

        return userSummary;
    }

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping("/get/users")
    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }
}