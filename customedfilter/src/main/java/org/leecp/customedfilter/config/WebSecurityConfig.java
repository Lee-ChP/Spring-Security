package org.leecp.customedfilter.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").hasAnyAuthority("Normal")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");

        security.addFilterAt(customFormLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    /**
     * 自定义认证过滤器
     */
    private CustomFormLoginFilter customFormLoginFilter() {
        return new CustomFormLoginFilter("/login");
    }
}
