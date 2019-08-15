package org.leecp.loginwithdb.web;

import lombok.AllArgsConstructor;
import org.leecp.loginwithdb.dto.User;
import org.leecp.loginwithdb.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping({"/", "/index", "/home"})
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(User user) {
        // 此处省略校验逻辑
        userService.saveUser(user);
        return "redirect:register?success";
    }
}