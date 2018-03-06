# 示例说明
所有示例的授权模式都为授权码模式(authorization code)

## JDBC模式的token存储
先执行sql语句建表 `\oauth2-server\src\main\resources\oauth2.sql`,该结构为Spring官方提供,也可以自己进行改造.  
注册客户端SQL  
INSERT INTO `oauth2`.`oauth_client_details`   
(`client_id`,   
`resource_ids`,   
`client_secret`,   
`scope`,   
`authorized_grant_types`,  
`web_server_redirect_uri`,   
`authorities`,   
`access_token_validity`,   
`refresh_token_validity`,   
`additional_information`,   
`autoapprove`)   
VALUES   
(  
'myClient',      -- 客户端id   
'oauth2-resource',  -- 资源id,可随意填  
'$2a$10$cNkm8sp0MojCcxcblqEeJORPD5PFgkT9KYZAq5hv1uSyT5ifoKfLm', -- 客户端密码  
'read,write,trust',  -- scope授权范围,这里可以自定义字符串,多个以逗号隔开  
'password,authorization_code,refresh_token,implicit,client_credentials',  -- 授权模式: 密码模式,授权码模式,刷新token,简化模式,客户端模式  
'',   
'ROLE_CLIENT,ROLE_TRUSTED_CLIENT', -- 角色,随便填  
'3600',  -- access_token有效时间,单位:秒  
NULL,   
'{}',   
'');  

注意: 客户端密码是经过了BCryptPasswordEncoder()编码,所以先将密码编码完再INSERT  
`String encodePassword = new BCryptPasswordEncoder().encode("你真实密码")`

注册用户SQL  
INSERT INTO `oauth2`.`users` (`username`, `password`, `enabled`) VALUES ('dave', 'secret', '1');

`oauth2-server`: 认证server  
`oauth2-resource`: 资源server (一般与认证server在一起,为演示单独独立出来)  
`oauth2-client`: 客户端,通过 `RestTemplate` 访问 `oauth2-server` 和 `oauth2-resource`
  
### 启动与演示
启动server和client,访问 `http://localhost:9081/client/get`  
此时页面跳转至 `http://localhost:9080/login`,输入注册的用户名和密码,通过认证后即可访问到认证服务器上的资源.  

## 内存模式的token存储
无需建表和配置(适合开发环境)  
`demo-oauth2-server`: 认证server,资源server  
`demo-oauth2-client`: 客户端,加入权限认证,`WebSecurityConfiguation`中配置的受权限
控制的url被访问时,先跳转到认证服务器登录页面,登陆后,拿到授权码,再利用授权码拿到access_token,再利用
access_token访问资源地址

### 启动与演示
启动server和client,访问 `http://localhost:8082/ui/`  
此时页面跳转至 `http://localhost:8081/auth/login`,输入注册的用户名:john和密码:123,通过认证后返回用户信息.

## GITHUB客户端
`demo-oauth2-githubclient` github oauth2 客户端  
首先需要有github账号,没有的可以花2分钟去注册一个,登录github, 在 `https://github.com/settings/developers` 界面注册 OAuth App  
配置Homepage URL : http://localhost:8090  
配置Authorization callback URL: http://localhost:8090/login/github  
注意此处配置的/login/github需要在filter中进行配置  
```java
private Filter sso() {
    OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
    OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
    githubFilter.setRestTemplate(githubTemplate);
    githubFilter.setTokenServices(new UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId()));
    return githubFilter;
}
```

### 启动与演示  
启动项目,访问 `http://localhost:8090/user/info`,此时页面跳转至 `http://localhost:8090/login`  
点击最下面的 `以GITHUB账户登录` 转至GITHUB授权登录页面,输入GITHUB用户密码,认证成功后重定向至 `http://localhost:8090/user/info`  
同时拿到了GITHUB的用户名称等信息
