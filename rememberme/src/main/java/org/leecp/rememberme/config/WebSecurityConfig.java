package org.leecp.rememberme.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/js/**","/css/**","/img/**","/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().invalidateHttpSession(true).clearAuthentication(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").permitAll()
                .and()
                .rememberMe().key("unique-and-secret").rememberMeCookieName("remember-me-cookie-name").tokenValiditySeconds(24 * 60 * 60);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("Lee").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
    }
}
