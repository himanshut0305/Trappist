package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.exception.AppException;
import in.co.qedtech.trappist.model.*;
import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.payload.SaveStudentRequest;
import in.co.qedtech.trappist.repository.RoleRepository;
import in.co.qedtech.trappist.repository.SchoolClassesRepository;
import in.co.qedtech.trappist.repository.StudentDetailsRepository;
import in.co.qedtech.trappist.repository.UserRepository;
import in.co.qedtech.trappist.security.CurrentUser;
import in.co.qedtech.trappist.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired private StudentDetailsRepository studentDetailsRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Autowired private RoleRepository roleRepository;
    @Autowired private SchoolClassesRepository schoolClassRepository;

    @Secured({"ROLE_STUDENT"})
    @PostMapping(value = "/get/studentDetails")
    public StudentDetails getStudentDetails(@CurrentUser UserPrincipal principal) {
        User currentUser = userRepository.getOne(principal.getId());
        return studentDetailsRepository.findByUser(currentUser);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value = "/save/student")
    public ResponseEntity<?> saveStudent(@Valid @RequestBody SaveStudentRequest request) {
        logger.info(request.toString());

        SchoolClass schoolClass = schoolClassRepository.getOne(request.getSchoolClassId());
        String username = (request.getRollNo() + "." + schoolClass.getName() + "@" + schoolClass.getSchoolYear().getSchool().getAka()).toLowerCase().replace("-", "");

        if (userRepository.existsByUsername(username)) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use"), HttpStatus.BAD_REQUEST);
        }

        String password = username + "00";
        User user = new User(request.getName(), username, request.getEmail(), password);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_STUDENT).orElseThrow(() -> new AppException("User Role not set"));
        user.setRoles(Collections.singleton(userRole));
        User savedUser = userRepository.save(user);

        StudentDetails studentDetails = new StudentDetails(request.getRollNo(), request.getAdmissionNo(), schoolClass, savedUser);
        studentDetailsRepository.save(studentDetails);
        return new ResponseEntity(new ApiResponse(true, "Student registered successfully"), HttpStatus.CREATED);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping(value = "/get/studentsBySchoolClass/{schoolClassId}")
    public List<StudentDetails> getStudentsBySchoolClass(@PathVariable(value = "schoolClassId") int schoolClassId) {
        SchoolClass schoolClass = schoolClassRepository.getOne(schoolClassId);
        return studentDetailsRepository.findBySchoolClass(schoolClass);
    }
}