package com.eim.controller;

import com.eim.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/index")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/hello")
    public String hello() {

        try {
            return testService.getHello();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
