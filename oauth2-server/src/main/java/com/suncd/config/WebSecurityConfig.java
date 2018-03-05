package com.suncd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 描述：设置访问权限拦截
 *
 * @author QUST
 * @version 1.0
 * @date 2017/4/11
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.antMatcher("/**")
//            .authorizeRequests()
//                .antMatchers("/", "/login**","/admin/**", "/webjars/**","/oauth/authorize").permitAll() //允许访问url通配
//                .anyRequest().authenticated()
//                .and().httpBasic()
//                .and().formLogin().permitAll()
//                .and().csrf().disable();

        http.requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }
}
