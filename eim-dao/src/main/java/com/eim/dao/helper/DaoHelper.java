package com.eim.dao.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class DaoHelper {

    //private final String NAME = "dynamicDataSourceLookupHelper";

    private final DynamicDataSourceLookupHelper dynamicDataSourceLookupHelper;

    private final JdbcTemplate jdbcTemplate;
    //private Class<T> clazz;

    @Autowired
    public DaoHelper(DynamicDataSourceLookupHelper dynamicDataSourceLookupHelper, JdbcTemplate jdbcTemplate) {
        //this.dynamicDataSourceLookupHelper = SpringContextUtil.getBean(NAME, DynamicDataSourceLookupHelper.class);
        this.dynamicDataSourceLookupHelper = dynamicDataSourceLookupHelper;
        this.jdbcTemplate = jdbcTemplate;

    }

    /**
     * 执行查询sql
     *
     * @param sql
     * @return
     */
    public <T> List<T> doQuery(String sql, Class<T> clazz) {
        return doQuery(sql, new Object[0], clazz);
    }


    public List<Map<String, Object>> doQuery(String sql) {
        return doQuery(sql, new Object[0]);
    }

    public <T> List<T> doQuery(String dsKey, String sql, Class<T> clazz) {
        return doQuery(dsKey, sql, new Object[0], clazz);
    }


    public List<Map<String, Object>> doQuery(String dsKey, String sql) {
        return doQuery(dsKey, sql, new Object[0]);
    }

    public <T> List<T> doQuery(String sql, Object[] objs, Class<T> clazz) {

       //// dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);

        BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);

        List<T> list = jdbcTemplate.query(sql, objs, rowMapper);

       // dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        return list;
    }

    public List<Map<String, Object>> doQuery(String sql, Object[] objs) {

     //   dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, objs);

      //  dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        return list;
    }

    /**
     * 执行查询sql
     *
     * @param dsKey 数据库key
     * @param sql
     * @param objs
     * @return
     */
    public <T> List<T> doQuery(String dsKey, String sql, Object[] objs, Class<T> clazz) {

        dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);

        BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);

        List<T> list = jdbcTemplate.query(sql, objs, rowMapper);

        dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        return list;
    }

    public List<Map<String, Object>> doQuery(String dsKey, String sql, Object[] objs) {

        dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, objs);

        dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        return list;
    }
}

