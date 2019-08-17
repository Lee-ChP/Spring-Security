package org.leecp.iplogin.security.filters;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class IpAuthenticationToken extends AbstractAuthenticationToken {
    private String ip;

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return this.ip;
    }

    /**
     * Step: 8 首先将用户信息拿去认证
     * @param ip
     */
    //该构造方法是认证时使用
    public IpAuthenticationToken (String ip) {
        super(null);
        this.ip = ip;
        System.out.println("This is IpAuthenticationToken(String ip)");
        super.setAuthenticated(false);
    }

    /**
     * Step 11： 验证成功后开始封装信息
     * @param ip
     * @param autorities
     */
    //该构造方法是认证成功后使用,封装用户信息
    public IpAuthenticationToken(String ip, Collection<? extends GrantedAuthority> autorities) {
        super(autorities);
        this.ip = ip;
        System.out.println("This is IpAuthenticationToken(String ip, Collection<? extends GrantedAuthority> autorities)");
        super.setAuthenticated(true);

    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.ip;
    }
}
