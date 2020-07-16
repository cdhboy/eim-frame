package com.eim.service.test.impl;

import com.eim.dao.helper.DaoHelper;
import com.eim.dao.helper.DynamicDataSourceLookupHelper;
import com.eim.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    @Override
    public String getHello() throws SQLException {
        DaoHelper<TestEntity> daoHelper = new DaoHelper<>(TestEntity.class);

        String sql = "select user_id ,user_name, user_sysid, date_format(add_time,'%Y-%m-%d') as add_time from sys_user";

       List<TestEntity> list = daoHelper.doQuery("hfzx",sql);

       return "" + list.size();

    }
}
