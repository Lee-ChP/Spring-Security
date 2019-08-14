package org.leecp.basic.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 如果不使用security框架，那么该接口可以直接访问
 * 现在有了security，必须登录。而每次运行都会在控制台生成一个随机密码，用户名为user
 * 为了不每次去控制台内找密码，可以在配置文件中配种用户与密码： application.yml
 */
@RestController
public class WelcomeController {
    @GetMapping("/")
    public String welcome() {
        return "Login Successfully, welcome .";
    }
}
