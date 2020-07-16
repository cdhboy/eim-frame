package com.eim.service.test;

import com.eim.service.BaseService;

import java.sql.SQLException;

public interface TestService extends BaseService {
    public String getHello() throws SQLException;
}
