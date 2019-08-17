package org.leecp.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class Oauth2ServerConfig {

    private static final String SOURCE_ID = "order";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfig extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(SOURCE_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity security) throws Exception {
            security
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                    .and()
                    .requestMatchers().anyRequest()
                    .and()
                    .anonymous()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/order/**").authenticated(); //配置order访问控制
        }

        @Configuration
        @EnableAuthorizationServer
        protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

            @Autowired
            AuthenticationManager authenticationManager;
            @Autowired
            RedisConnectionFactory redisConnectionFactory;

            @Autowired
            /**
             * 配置客户端相关信息
             */
            public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

                //配置两个客户端，一个用于password认证，一个用于client认证
                clients.inMemory()
                        .withClient("client_1").resourceIds(SOURCE_ID).authorizedGrantTypes("client_credentials", "refresh_token").scopes("select").authorities("client").secret("123456")
                        .and()
                        .withClient("client_2").resourceIds(SOURCE_ID).authorizedGrantTypes("password","refresh_token").scopes("select").authorities("client").secret("123456");
            }

            @Override
            /**
             * 配置AuthorizationServerEndpointsConfigurer众多相关类，
             * 包括配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
             */
            public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
                endpoints
                        .tokenStore(new RedisTokenStore(redisConnectionFactory))
                        .authenticationManager(authenticationManager);
            }

            /**
             *  配置AuthorizationServer安全认证的相关信息，创建ClientCredentialsTokenEndpointFilter核心过滤器
             * @param oauthServer
             * @throws Exception
             */
            public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
                //允许表单认证
                oauthServer.allowFormAuthenticationForClients();            }
        }

    }

}
