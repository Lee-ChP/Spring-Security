package org.leecp.iplogin.security.config;

import org.leecp.iplogin.security.filters.IpAuthenticationProcessingFilter;
import org.leecp.iplogin.security.filters.IpAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //ip认证者配置

    /**
     * Step 2: 核心构建器构建完毕后，调用自己的实现类provider，该类提供基本的认证逻辑以及方法
     * @return
     */
    @Bean
    public IpAuthenticationProvider ipAuthenticationProvider() {
        System.out.println("This is websecurityconfig ipAuthenticationProvider()");
        return new IpAuthenticationProvider();
    }

    //配置封装token的过滤器

    /**
     * 过滤链入口配置后后，为过滤器添加认证器
     * Step 5
     * @param authenticationManager
     * @return
     */
    public IpAuthenticationProcessingFilter ipAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        System.out.println("This is websecurityconfig ipAuthenticationProcessingFilter()");
        IpAuthenticationProcessingFilter ipAuthenticationProcessingFilter = new IpAuthenticationProcessingFilter();
        //为过滤器添加认证器
        ipAuthenticationProcessingFilter.setAuthenticationManager(authenticationManager);
        //重写认证失败时的跳转页面
        ipAuthenticationProcessingFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/ipLogin?error"));
        return ipAuthenticationProcessingFilter;
    }

    //配置登录端点

    /**
     * Step: 4： 过滤链配置完之后初始化过滤链入口
     * @return
     */
    @Bean
    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        System.out.println("This is websecurityconfig loginUrlAuthenticationEntryPoint()");
        LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint = new LoginUrlAuthenticationEntryPoint("/ipLogin");
        return loginUrlAuthenticationEntryPoint;
    }

    /**
     * Step : 1 ： 构建核心验证器
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth){

        System.out.println("This is websecurityconfig configure(AuthenticationManagerBuilder auth)");

        auth.authenticationProvider(ipAuthenticationProvider());
    }

    /**
     * Step： 3 ： provider类调用完后开始构建过滤链
     * @param security
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        System.out.println("This is websecurityconfig configure(HttpSecurity security)");

        security
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/ipLogin").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/ipLogin").authenticationEntryPoint(loginUrlAuthenticationEntryPoint());

        //注册IpAuthenticationProcessFilter,放置的顺序很重要
        security.addFilterBefore(ipAuthenticationProcessingFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
    }

}
