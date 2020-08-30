package in.co.qedtech.trappist.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class PageController {
    private static final Logger logger = LoggerFactory.getLogger(PageController.class);

    @RequestMapping("/login")
    @PreAuthorize("permitAll()")
    public String login() {
        return "login.html";
    }

    @RequestMapping("/home")
    @PreAuthorize("permitAll()")
    public String homepage() {
        return "users.html";
    }
}
