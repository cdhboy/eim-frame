package com.eim.controller;

import com.eim.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/hello")
    public String hello() {
        return testService.getHello();
    }
}
