package org.leecp.iplogin.security.filters;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class IpAuthenticationProvider implements AuthenticationProvider {

    final static Map<String, SimpleGrantedAuthority> ipAuthorityMap = new ConcurrentHashMap<>();

    //维护一个白名单列表，每个ip对应相应的权限
    static {
        ipAuthorityMap.put("127.0.0.1", new SimpleGrantedAuthority("ADMIN"));
        ipAuthorityMap.put("192.168.0.151.", new SimpleGrantedAuthority("ADMIN"));
        ipAuthorityMap.put("192.168.0.152", new SimpleGrantedAuthority("FRIEND"));

    }

    /**
     * Step 10 进行认证，成功后调用step 11，让step 11进行用户信息打包
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("This is authenticate()");
        IpAuthenticationToken ipAuthenticationToken = (IpAuthenticationToken) authentication;
        String ip = ipAuthenticationToken.getIp();
        SimpleGrantedAuthority simpleGrantedAuthority = ipAuthorityMap.get(ip);
        //不在白名单列表中
        if (simpleGrantedAuthority == null) {
            return null;
        } else {
            //封装权限信息，此时身份已经被认证,返回一个经过认证的token
            return new IpAuthenticationToken(ip, Arrays.asList(simpleGrantedAuthority));
        }
    }

    /**
     * Step ： 9 判断支持认证的方法
     * @param aClass
     * @return
     */
    //只支持IpAuthenticationToken该身份
    @Override
    public boolean supports(Class<?> aClass) {
        System.out.println("This is supports()");
        return (IpAuthenticationToken.class.isAssignableFrom(aClass));
    }
}
