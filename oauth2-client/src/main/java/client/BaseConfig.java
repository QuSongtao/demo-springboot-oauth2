/*
 * 版权所有 © 成都太阳高科技有限责任公司
 * http://www.suncd.com
 */
package client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

/**
 * 功能：XXXX
 *
 * @author qust
 * @version 1.0 2018/3/1 10:21
 */
@Configuration
public class BaseConfig {
    @Bean
    public SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();
        Properties exceptionMappings = new Properties();
        exceptionMappings.put(
                "com.test.commons.model.exceptions.NotFound", "error/404");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");
        exceptionResolver.setExceptionMappings(exceptionMappings);
        Properties statusCodes = new Properties();
        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");
        exceptionResolver.setStatusCodes(statusCodes);
        // ADDED TO IGNORE THRE USER_REDIRECT_REQUIRED_EXCEPTION
        exceptionResolver
                .setExcludedExceptions(UserRedirectRequiredException.class);
        return exceptionResolver;
    }
}
