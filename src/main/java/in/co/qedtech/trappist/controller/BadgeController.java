package in.co.qedtech.trappist.controller;

import in.co.qedtech.trappist.model.Badge;
import in.co.qedtech.trappist.payload.SaveBadgeRequest;
import in.co.qedtech.trappist.repository.BadgeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class BadgeController {
    private static final Logger logger = LoggerFactory.getLogger(ChapterController.class);

    @Autowired
    BadgeRepository badgeRepository;
//
//    @Secured({"ROLE_SUPER_ADMIN", "ROLE_SUBJECT_EXPERT", "ROLE_CONTRIBUTOR"})
//    @PostMapping("/save/badge")
//    public Badge saveBadge(@Valid @RequestBody SaveBadgeRequest saveBadgeRequest){
//        Badge badge= new Badge();
//        badge.setName(saveBadgeRequest.getName());
//        badge.setFromValue(saveBadgeRequest.getFrom());
//        badge.setToValue(saveBadgeRequest.getTo());
//        badge.setKpi(saveBadgeRequest.getKpi());
//        badge.setDescription(saveBadgeRequest.getDescription());
//
//        logger.info(saveBadgeRequest.toString());
//        //return badgeRepository.save(badge);
//        return null;
//    }

}
