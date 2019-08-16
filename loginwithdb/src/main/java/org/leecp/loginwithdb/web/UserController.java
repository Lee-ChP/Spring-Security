package org.leecp.loginwithdb.web;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController {

    @GetMapping("/user")
    public String user( Model model){
        model.addAttribute("success", "LoginSuccessfully");
        return "user/user";
    }

    @GetMapping("/user/update")
    public String updateAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> newAuth = new ArrayList<>(auth.getAuthorities());
        newAuth.add(new SimpleGrantedAuthority("ROLE_Vip"));
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),newAuth);
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);
        return "updateAuth";
    }


    /**
     * ROLE_前缀是必须的
     * @return
     */
    @GetMapping("/vip")
    @Secured("ROLE_Vip")
    public String vip(){
        return "vip";
    }

}