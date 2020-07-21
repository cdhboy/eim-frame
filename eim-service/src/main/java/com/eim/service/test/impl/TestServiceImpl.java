package com.eim.service.test.impl;

import com.eim.dao.annotation.TargetDataSource;
import com.eim.dao.helper.DaoHelper;
import com.eim.dao.helper.DynamicDataSourceLookupHelper;
import com.eim.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
@TargetDataSource("hfzx")
public class TestServiceImpl implements TestService {

    @Autowired
    private DaoHelper daoHelper;

    @Autowired
    DynamicDataSourceLookupHelper dynamicDataSourceLookupHelper;

    @Override
    @TargetDataSource()
    public String getHello() throws SQLException {
        //  DaoHelper<TestEntity> daoHelper = new DaoHelper<>(TestEntity.class);

        String sql = "select user_id from sys_user";

        List<Map<String, Object>> list1 = daoHelper.doQuery( sql);
        // dynamicDataSourceLookupHelper.changeDataSourceByKey("hfzx");


        //BeanPropertyRowMapper<TestEntity> rowMapper = new BeanPropertyRowMapper<>(TestEntity.class);

       // List<TestEntity> list2 = daoHelper.doQuery( sql, TestEntity.class);

        // List<Map<String, Object>> list2 = jdbcTemplate.queryForList(sql);

        //List<TestEntity> list = daoHelper.doQuery("hfzx",sql);

        return "1:" + list1.size();

    }
}
