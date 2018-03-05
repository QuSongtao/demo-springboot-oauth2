package client;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * 描述：OAuth2客户端配置，通过访问资源服务器
 *
 */
@Configuration
@EnableAutoConfiguration
@EnableOAuth2Client
@RestController
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	//基础路径
	@Value("${oauth.resource:http://localhost:9080}")
	private String baseUrl;

	//认证服务器认证url
	@Value("${oauth.authorize:http://localhost:9080/oauth/authorize}")
	private String authorizeUrl;

	//认证服务器token的url
	@Value("${oauth.token:http://localhost:9080/oauth/token}")
	private String tokenUrl;
	
	@Autowired
	private OAuth2RestOperations restTemplate;

//	@Autowired
//    private OAuth2RestTemplate restTemplate2;

	@Autowired
    private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

//	@Autowired
//	private OAuth2ProtectedResourceDetails auth2ProtectedResourceDetails;
	
	@RequestMapping("/get")
	public List<String> home() {
        List<String> result = new ArrayList<>();
        result.add("hello");
		result = restTemplate.getForObject(baseUrl + "/gx", List.class);
		return result;
	}


	@Bean
	public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext) {
		return new OAuth2RestTemplate(resource(), oauth2ClientContext);
	}

	@Bean
	protected OAuth2ProtectedResourceDetails resource() {
		AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
		resource.setId("oauth2-resource");
		resource.setClientId("myClient");
		resource.setClientSecret("cgx");
		resource.setAccessTokenUri(tokenUrl);
		resource.setUserAuthorizationUri(authorizeUrl);
		resource.setScope(Arrays.asList("read","trust","write"));
		return resource ;
	}
	
}
