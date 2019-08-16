package org.leecp.rememberme.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String greeting(){
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
