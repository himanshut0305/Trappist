package in.co.qedtech.trappist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import in.co.qedtech.trappist.exception.AppException;
import in.co.qedtech.trappist.exception.BadRequestException;
import in.co.qedtech.trappist.model.Role;
import in.co.qedtech.trappist.model.RoleName;
import in.co.qedtech.trappist.model.User;
import in.co.qedtech.trappist.model.JwtRefreshToken;
import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.payload.JwtAuthenticationResponse;
import in.co.qedtech.trappist.payload.LoginRequest;
import in.co.qedtech.trappist.payload.RefreshTokenRequest;
import in.co.qedtech.trappist.payload.SignUpRequest;
import in.co.qedtech.trappist.repository.JwtRefreshTokenRepository;
import in.co.qedtech.trappist.repository.RoleRepository;
import in.co.qedtech.trappist.repository.UserRepository;
import in.co.qedtech.trappist.security.JwtTokenProvider;
import in.co.qedtech.trappist.security.UserPrincipal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired AuthenticationManager authenticationManager;
    @Autowired UserRepository userRepository;
    @Autowired RoleRepository roleRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired JwtTokenProvider tokenProvider;
    @Autowired JwtRefreshTokenRepository jwtRefreshTokenRepository;

    @Value("${app.jwtExpirationInMs}") private long jwtExpirationInMs;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String accessToken = tokenProvider.generateToken(userPrincipal);
        String refreshToken = tokenProvider.generateRefreshToken();

        saveRefreshToken(userPrincipal, refreshToken);

        return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, refreshToken, jwtExpirationInMs));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return jwtRefreshTokenRepository.findById(refreshTokenRequest.getRefreshToken()).map(jwtRefreshToken -> {
            User user = jwtRefreshToken.getUser();
            String accessToken = tokenProvider.generateToken(UserPrincipal.create(user));
            return ResponseEntity.ok(new JwtAuthenticationResponse(accessToken, jwtRefreshToken.getToken(), jwtExpirationInMs));
        }).orElseThrow(() -> new BadRequestException("Invalid Refresh Token"));
    }

    private void saveRefreshToken(UserPrincipal userPrincipal, String refreshToken) {
        JwtRefreshToken jwtRefreshToken = new JwtRefreshToken(refreshToken);
        jwtRefreshToken.setUser(userRepository.getOne(userPrincipal.getId()));

        Instant expirationDateTime = Instant.now().plus(360, ChronoUnit.DAYS);  // Todo Add this in application.properties
        jwtRefreshToken.setExpirationDateTime(expirationDateTime);
        jwtRefreshTokenRepository.save(jwtRefreshToken);
    }

    @PostMapping(value="/init", produces = {"application/json"}, consumes = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> registerSuperUser() {
        List<User> users = userRepository.findAll();

        ArrayList<Role> roles = new ArrayList<>();

        roles.add(new Role(RoleName.ROLE_SUPER_ADMIN));
        roles.add(new Role(RoleName.ROLE_SUBJECT_EXPERT));
        roles.add(new Role(RoleName.ROLE_CONTRIBUTOR));
        roles.add(new Role(RoleName.ROLE_SCHOOL_ADMIN));
        roles.add(new Role(RoleName.ROLE_TEACHER));
        roles.add(new Role(RoleName.ROLE_STUDENT));
        roles.add(new Role(RoleName.ROLE_USER));

        roleRepository.saveAll(roles);

        User user = new User("System Administrator", "sa@qed", "contact@qedtech.co.in", "QED@Jhabbu@123");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN).orElseThrow(() -> new AppException("User Role not set"));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        return new ResponseEntity(new ApiResponse(true, "User registered successfully"), HttpStatus.CREATED);

    }

    @Secured("ROLE_SUPER_ADMIN")
    @PostMapping(value="/registerUser", produces = {"application/json"}, consumes = {"application/json; charset=UTF-8"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        logger.info(signUpRequest.toString());

        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken"), HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(signUpRequest.getRole()).orElseThrow(() -> new AppException("User Role not set"));
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

        return new ResponseEntity(new ApiResponse(true, "User registered successfully"), HttpStatus.CREATED);
    }

    @RequestMapping(value="/getUserDetails")
    @Secured("ROLE_SUPER_ADMIN")
    public String getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getPrincipal().toString();
        }

        return "users.html";
    }
}
