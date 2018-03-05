package com.suncd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import javax.sql.DataSource;

/**
 * 描述：SpringBoot OAuth2 Server 认证服务器
 * 以JDBC方式存储：
 *    1）oauth_access_token 认证码信息
 *    2）oauth_client_details 客户端注册信息
 *    3）users 用户信息
 *    4）authorities 用户关联的角色信息
 *
 * @author QUST
 * @version 1.0  2017-04-06
 *
 */
@SpringBootApplication
public class Oauth2ServerBoot {

	@Autowired
	private DataSource dataSource;

	/**
	 * 指定认证存储方式为：JDBC
	 * 生产中：采用其他方式在表USERS和AUTHORITIES中添加用户、密码、角色数据
	 * @param auth
	 */
	@Autowired
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		// @formatter:off
		auth.jdbcAuthentication().dataSource(dataSource);
				 //特定用户
				/*.withUser("dave")
				.password("secret").roles("USER", "ACTUATOR");*/
		// @formatter:on
	}

	/**
	 * 程序入口
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Oauth2ServerBoot.class, args);
	}

}
