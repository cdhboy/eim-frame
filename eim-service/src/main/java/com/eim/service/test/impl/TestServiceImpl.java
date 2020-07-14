package com.eim.service.test.impl;

import com.eim.service.test.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String getHello() {
        return "hello";
    }
}
