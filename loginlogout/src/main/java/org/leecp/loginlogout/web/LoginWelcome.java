package org.leecp.loginlogout.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginWelcome {
    @GetMapping("/")
    public String welcome() {
        return "Login Successfully,  Login and Logout demo";
    }
}
