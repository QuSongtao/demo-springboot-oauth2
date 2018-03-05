package com.suncd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * 描述：授权服务器
 * 最精简配置，客户端及token信息保存在数据库中
 *
 * @author QUST
 * @version 1.0
 * @date 2017/4/5
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager auth;

    @Autowired
    private DataSource dataSource;

    //定义密码编码器，在数据库中显示为密文
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 定义access_token存储方式
     * 方式：为数据库存储
     * @return
     */
    @Bean
    public JdbcTokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    protected AuthorizationCodeServices authorizationCodeServices() {
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security)
            throws Exception {
        security.passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {
        endpoints.authorizationCodeServices(authorizationCodeServices())
                .authenticationManager(auth)
                .tokenStore(tokenStore())
                .userDetailsService(new ClientDetailsUserDetailsService(new JdbcClientDetailsService(dataSource)));
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // @formatter:off
        clients.jdbc(dataSource)
                .passwordEncoder(passwordEncoder)
        //预定义Client信息，即每次应用启动的时候往数据库auth_client_details表中插入以下3条Client数据
                /*.withClient("myClient").secret("cgx")
                    .authorizedGrantTypes("password", "authorization_code","refresh_token", "implicit")
                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                    .scopes("read", "write", "trust")
                    .resourceIds("oauth2-resource")
                    .accessTokenValiditySeconds(60).and()
                .withClient("my-client-with-registered-redirect")
                    .authorizedGrantTypes("authorization_code")
                    .authorities("ROLE_CLIENT").scopes("read", "trust")
                    .resourceIds("oauth2-resource")
                    .redirectUris("http://anywhere?key=value").and()
                .withClient("my-client-with-secret")
                    .authorizedGrantTypes("client_credentials", "password")
                    .authorities("ROLE_CLIENT").scopes("read")
                    .resourceIds("oauth2-resource").secret("secret")*/;
        // @formatter:on
    }
}
