/*
 * 版权所有 © 成都太阳高科技有限责任公司
 * http://www.suncd.com
 */
package com.suncd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：XXXX
 *
 * @author qust
 * @version 1.0 2018/2/28 15:35
 */
@RestController
public class TestController {
    @Autowired
    private JdbcTokenStore jdbcTokenStore;

    @RequestMapping("/gx")
    public List<String> getInfo(){
        System.out.println("so far!");
        List<String> s = new ArrayList<>();
        s.add("cgx");
        s.add("happy");
        return s;
    }
}
