package org.leecp.iplogin.security.filters;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IpAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * Step 6： 开始浏览，先到入口端点
     */
    //使用 /ipVerify端点对ip进行认证
    public IpAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/ipVerify"));
        System.out.println("This is IpAuthenticationProcessingFilter()");
    }

    /**
     * Step 7： 接着跳转到登录页面，点击登录后，该方法获取用户信息
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        System.out.println("This is attemptAuthentication()");
        //获取host信息
        String host = httpServletRequest.getRemoteHost();
        if (host.equals("0:0:0:0:0:0:0:1")) {
            host = "127.0.0.1";
        }
        //交给内部AutenticationManager去认证，实现解耦
        return getAuthenticationManager().authenticate(new IpAuthenticationToken(host));
    }
}
