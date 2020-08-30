package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.exception.AppException;
import in.co.qedtech.trappist.model.*;
import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.payload.AssignSubjectToTeacher;
import in.co.qedtech.trappist.payload.SaveTeacherRequest;
import in.co.qedtech.trappist.repository.*;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TeacherController {
    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private RoleRepository roleRepository;

    @Autowired private SchoolRepository schoolRepository;
    @Autowired private SubjectRepository subjectRepository;
    @Autowired private SchoolClassesRepository schoolClassesRepository;

    @Autowired private SchoolClassSubjectRepository schoolClassSubjectRepository;
    @Autowired private TeacherDetailsRepository teacherDetailsRepository;
    @Autowired private SchoolYearSubjectBookRepository schoolYearSubjectBookRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/save/teacher")
    public ResponseEntity<?> saveTeacher(@Valid @RequestBody SaveTeacherRequest request) {
        logger.info(request.toString());

        School school = schoolRepository.getOne(request.getSchoolId());
        String username = (request.getUsername() + "@" + school.getAka()).toLowerCase().replace("-", "");

        if (userRepository.existsByUsername(username)) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use"), HttpStatus.BAD_REQUEST);
        }

        String password = username + "00";
        User user = new User(request.getName(), username, request.getEmail(), password);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_TEACHER).orElseThrow(() -> new AppException("User Role not set"));
        user.setRoles(Collections.singleton(userRole));

        logger.info("User Details" + user.toString());

        User savedUser = userRepository.save(user);

        TeacherDetails teacherDetails = new TeacherDetails(request.getPhoneNo(), request.getEmail(), school, savedUser);
        teacherDetailsRepository.save(teacherDetails);
        return new ResponseEntity(new ApiResponse(true, "Teacher registered successfully"), HttpStatus.CREATED);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping(value="/assign/subjectToTeacher")
    public TeacherDetails assignSubjectsToTeacher(@Valid @RequestBody AssignSubjectToTeacher request) {
        TeacherDetails teacher = teacherDetailsRepository.getOne(request.getTeacherId());
        SchoolClass schoolClass = schoolClassesRepository.getOne(request.getSchoolClassId());
        Subject subject = subjectRepository.getOne(request.getSubjectId());

        SchoolClassSubject schoolClassSubject = schoolClassSubjectRepository.findBySchoolClassAndSubject(schoolClass, subject);

        Set<SchoolClassSubject> schoolClassSubjects  = teacher.getSchoolClassSubjects();

        if(schoolClassSubjects == null) {
            schoolClassSubjects = new HashSet<>();
            schoolClassSubjects.add(schoolClassSubject);
        }
        else {
            schoolClassSubjects.add(schoolClassSubject);
        }

        teacher.setSchoolClassSubjects(schoolClassSubjects);
        teacherDetailsRepository.save(teacher);

        return teacherDetailsRepository.save(teacher);
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping(value="/get/teachers")
    public List<TeacherDetails> getTeachers() {
        return teacherDetailsRepository.findAll();
    }

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping(value="/get/teachersForSchool/{schoolId}")
    public List<TeacherDetails> getTeachersForSchool(@PathVariable(value = "schoolId") int schoolId) {
        School school = schoolRepository.getOne(schoolId);
        return teacherDetailsRepository.findBySchool(school);
    }
}