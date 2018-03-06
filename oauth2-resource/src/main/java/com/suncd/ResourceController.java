package com.suncd;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：XXXXX
 *
 * @author QUST
 * @version 1.0
 * @date 2017/4/10
 */
@RestController
public class ResourceController {
    /**
     * 测试资源访问1
     * @return String
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String get(){
        return "Hello World!";
    }

    /**
     * 测试资源访问2
     * @return String
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return "Hello cgx!";
    }

    /**
     * 测试资源访问3
     * @return String
     */
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test(){
        return "Hello test cgx!";
    }
}
