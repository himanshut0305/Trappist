package in.co.qedtech.trappist.controller;


import in.co.qedtech.trappist.model.SchoolChain;
import in.co.qedtech.trappist.payload.ApiResponse;
import in.co.qedtech.trappist.repository.SchoolChainRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
class SchoolChainController {
    private static final Logger logger = LoggerFactory.getLogger(SchoolChainController.class);

    @Autowired SchoolChainRepository schoolChainRepository;

    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
    @GetMapping("/get/schoolChains")
    public List<SchoolChain> getSchoolChains() {
        List<SchoolChain> schoolChainList = schoolChainRepository.findAll();
        return schoolChainList;
    }

    @Secured("ROLE_SUPER_ADMIN")
    @GetMapping(value="/save/schoolChain/{name:.+}+{alias:.+}")
    public ResponseEntity<?> saveSchoolChain(@PathVariable String name, @PathVariable String alias) {
        if(schoolChainRepository.existsByName(name)) {
            return new ResponseEntity(new ApiResponse(false, "Name already exists"), HttpStatus.BAD_REQUEST);
        }

        if(schoolChainRepository.existsByAka(alias)) {
            return new ResponseEntity(new ApiResponse(false, "Alias already exists"), HttpStatus.BAD_REQUEST);
        }

        SchoolChain schoolChain = new SchoolChain(name, alias);
        schoolChainRepository.save(schoolChain);
        return new ResponseEntity(new ApiResponse(true, "School Chain created successfully"), HttpStatus.CREATED);
    }
}
