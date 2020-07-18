package com.eim.service.test.impl;

import com.eim.dao.helper.DaoHelper;
import com.eim.dao.helper.DynamicDataSourceLookupHelper;
import com.eim.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private DaoHelper daoHelper;

    @Autowired
    DynamicDataSourceLookupHelper dynamicDataSourceLookupHelper;

    @Override
    public String getHello() throws SQLException {
        //  DaoHelper<TestEntity> daoHelper = new DaoHelper<>(TestEntity.class);

        String sql = "select user_id ,user_name, user_sysid, date_format(add_time,'%Y-%m-%d') as add_time from sys_user";

        List<Map<String, Object>> list1 = daoHelper.doQuery("hfzx", sql);
        // dynamicDataSourceLookupHelper.changeDataSourceByKey("hfzx");


        //BeanPropertyRowMapper<TestEntity> rowMapper = new BeanPropertyRowMapper<>(TestEntity.class);

        List<TestEntity> list2 = daoHelper.doQuery( sql, TestEntity.class);

        // List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql);

        //List<TestEntity> list = daoHelper.doQuery("hfzx",sql);

        return "1:" + list1.size() + ",  2:" + list2.size();

    }
}
