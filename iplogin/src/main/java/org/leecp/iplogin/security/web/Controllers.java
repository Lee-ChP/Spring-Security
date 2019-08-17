package org.leecp.iplogin.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Controllers {

    @RequestMapping({"/","/home"})
    public String index() {
        return "index";
    }
    @RequestMapping("/hello")
    public String hello() {
        return "greeting";
    }

    @RequestMapping("/ip")
    public String ipHello() {
        return "ipGreeting";
    }
    @RequestMapping("/ipLogin")
    public String login() {
        return "loginWithIp";
    }
}
