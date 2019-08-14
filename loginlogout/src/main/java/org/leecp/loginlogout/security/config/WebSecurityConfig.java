package org.leecp.loginlogout.security.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



//启用Security注解的话，需要继承WebSecurityConfigurerAdapter抽象类，两者结合才能完成security的配置；
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * @desc 重写 configure(AuthenticationManagerBuilder authenticationManager)
     *       AuthenticationManagerBuilder 用于创建一个AuthenticationManager ,
     *       而这个玩意的作用就是实现内存验证、LADP验证、基于JDBC的验证、添加UserDetailsService、添加AuthenticationProvider。
     *       如果没有重写这个也没关系，会自动获取一个，
     *       这里重写该方法是为了不去使用数据库
     * @param builder :
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {

        //使用authenticationManager创建几个内存用户并且分配权限
        //现在有一个管理员一个普通用户
        builder.inMemoryAuthentication()
                /**
                 * 这个demo在内存用户密码未加密的时候，并不能成功运行
                 * 主要的原因是，在定义的规则中，使用了内存用户和密码，但是并没有加密。
                 * 然后在spring 5.x中，密码存储的格式为{id}...... 这个id是加密方式，比如sha256等等，后面则是加密密码
                 * 应用拿到手后，首先查id，也就是加密方式，没有定义的话，为null，没有null这种加密方式，就报错了
                 * 所以passwordEncoder是必须的，紧挨着inMemoryAuthentication()去定义
                 *
                 * id是必须的，id最先定义，且id是一个加密方式； 最后将密码使用id对应的加密方式加密 （这是很关键的一步）
                 *
                 * 在低版本中，允许不加密
                 */
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser("Lee-ChP").password(new BCryptPasswordEncoder().encode("123456")).roles("Admin","Normal")
                .and()
                .withUser("Guest").password(new BCryptPasswordEncoder().encode("111111")).roles("Normal");
    }

    /**
     * 覆写这个方法是为了定义安全规则
     * @param security
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security
                //开启基于HttpServletRequest的访问限制
                .authorizeRequests()
                    // 可以调用  .antMatchers()再配合.hasRole对单个请求资源进行鉴权
                   // .antMatchers("/").hasRole("Normal")
                    //任何request请求,
                    .anyRequest()
                        //都需要验证
                        .authenticated()
                .and()
                //开启基于表单的身份验证： 如果没有指定loginPage，那么就会自动生成一个登录页面
                .formLogin()
                .and()
                //启用http basic 验证
                .httpBasic();

    }


}
