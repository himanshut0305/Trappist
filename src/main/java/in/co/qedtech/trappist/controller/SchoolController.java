package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.*;
import in.co.qedtech.trappist.payload.*;
import in.co.qedtech.trappist.repository.BoardRepository;
import in.co.qedtech.trappist.repository.SchoolChainRepository;
import in.co.qedtech.trappist.service.FileStorageService;
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

import in.co.qedtech.trappist.repository.SchoolRepository;

@RestController
@RequestMapping("/api")
public class SchoolController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    @Autowired private FileStorageService fileStorageService;

    @Autowired SchoolRepository schoolRepository;
    @Autowired SchoolChainRepository schoolChainRepository;
    @Autowired BoardRepository boardRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/schools")
    public List<School> getSchools() {
        List<School> schoolList = schoolRepository.findAll();
        return schoolList;
    }

    @PostMapping("/get/schoolByCode")
    public School getSchoolByCode(@Valid @RequestBody GetSchoolByCodeRequest request) {
        return schoolRepository.findBySchoolCode(request.getCode());
    }


    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @PostMapping("/get/schoolsByBoardAndGroup")
    public List<School> getSchoolsByBoardAndGroup(@Valid @RequestBody GetSchoolsRequest request) {
        Board board = boardRepository.getOne(request.getBoardId());
        SchoolChain schoolChain = schoolChainRepository.getOne(request.getGroupId());

        List<School> schoolList = schoolRepository.findByBoardAndSchoolChain(board,schoolChain);
        return schoolList;
    }

    @Secured("ROLE_SUPER_ADMIN")
    @PostMapping(value="/save/school")
    public ResponseEntity<?> saveSchool(@Valid @RequestBody SaveSchoolRequest request) {
        logger.info(request.toString());
        if(schoolRepository.existsByName(request.getName())) {
            return new ResponseEntity(new ApiResponse(false, "Name already exists"), HttpStatus.BAD_REQUEST);
        }

        if(schoolRepository.existsByAka(request.getAlias())) {
            return new ResponseEntity(new ApiResponse(false, "Alias already exists"), HttpStatus.BAD_REQUEST);
        }

        if(schoolRepository.existsBySchoolCode(request.getCode())) {
            return new ResponseEntity(new ApiResponse(false, "Code already exists"), HttpStatus.BAD_REQUEST);
        }

        String logoURL = fileStorageService.storeBufferedImage(request.getLogo());

        SchoolChain schoolChain = null;
        if(request.getSchoolChainId() != 0 && request.getSchoolChainId() != -1) {
            schoolChain = schoolChainRepository.getOne(request.getSchoolChainId());
        }

        Board board = boardRepository.getOne(request.getBoardId());
        School school = new School(request.getName(), request.getAlias(), request.getCode(), board, "", null, schoolChain, logoURL, request.getTheme());

        schoolRepository.save(school);

        return new ResponseEntity(new ApiResponse(true, "School created successfully"), HttpStatus.CREATED);
    }
}

