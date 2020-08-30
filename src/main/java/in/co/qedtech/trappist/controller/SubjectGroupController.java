package in.co.qedtech.trappist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

import in.co.qedtech.trappist.model.SubjectGroup;
import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.payload.SaveSubjectGroupRequest;
import in.co.qedtech.trappist.repository.SubjectGroupRepository;

@RestController
@RequestMapping("/api")
class SubjectGroupController {
    private static final Logger logger = LoggerFactory.getLogger(SubjectGroupController.class);

    @Autowired SubjectGroupRepository subjectGroupRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/subjectGroups")
    public List<SubjectGroup> getSubjectGroups(){
        List<SubjectGroup> subjectGroupList = subjectGroupRepository.findAll();
        return subjectGroupList;
    }

    @Secured("ROLE_SUPER_ADMIN")
    @PostMapping(value="/save/subjectGroup")
    public ResponseEntity<?> saveSubjectGroup(@Valid @RequestBody SaveSubjectGroupRequest saveSubjectGroupRequest) {
        if(subjectGroupRepository.existsByName(saveSubjectGroupRequest.getName())) {
            return new ResponseEntity(new ApiResponse(false, "Name already exists"), HttpStatus.BAD_REQUEST);
        }

        SubjectGroup subjectGroup = new SubjectGroup(saveSubjectGroupRequest.getName());
        subjectGroupRepository.save(subjectGroup);
        return new ResponseEntity(new ApiResponse(true, "Subject Group created successfully"), HttpStatus.CREATED);
    }
}
