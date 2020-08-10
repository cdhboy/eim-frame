package com.eim.dao.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DaoHelper {

    //private final String NAME = "dynamicDataSourceLookupHelper";
    private final Log logger = LogFactory.getLog(this.getClass());

    private final DynamicDataSourceLookupHelper dynamicDataSourceLookupHelper;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DaoHelper(DynamicDataSourceLookupHelper dynamicDataSourceLookupHelper, JdbcTemplate jdbcTemplate) {
        this.dynamicDataSourceLookupHelper = dynamicDataSourceLookupHelper;
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 执行查询sql
     *
     * @param sql   查询sql
     * @param clazz 实体类
     * @param <T>
     * @return
     */
    public <T> List<T> doQuery(String sql, Class<T> clazz) {
        return doQuery(sql, new Object[0], clazz);
    }

    /**
     * 执行查询sql
     *
     * @param sql 查询sql
     * @return
     */
    public List<Map<String, Object>> doQuery(String sql) {
        return doQuery(sql, new Object[0]);
    }

    /**
     * 执行查询sql
     *
     * @param dsKey 数据源key
     * @param sql   查询sql
     * @param clazz 实体类对象
     * @param <T>
     * @return
     */
    public <T> List<T> doQuery(String dsKey, String sql, Class<T> clazz) {
        return doQuery(dsKey, sql, new Object[0], clazz);
    }

    /**
     * 执行查询sql
     *
     * @param dsKey 数据源key
     * @param sql   查询sql
     * @return
     */
    public List<Map<String, Object>> doQuery(String dsKey, String sql) {
        return doQuery(dsKey, sql, new Object[0]);
    }

    /**
     * 执行查询sql
     *
     * @param sql   查询sql
     * @param objs  sql传入参数
     * @param clazz 实体类
     * @param <T>
     * @return
     */
    public <T> List<T> doQuery(String sql, Object[] objs, Class<T> clazz) {
        BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);
        List<T> list = jdbcTemplate.query(sql, objs, rowMapper);

        logger.info(sql);

        return list;
    }

    /**
     * 执行查询sql
     *
     * @param sql  查询sql
     * @param objs sql传入参数
     * @return
     */
    public List<Map<String, Object>> doQuery(String sql, Object[] objs) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, objs);

        logger.info(sql);

        return list;
    }

    /**
     * 执行查询sql
     *
     * @param dsKey 数据源key
     * @param sql   查询sql
     * @param objs  sql传入参数
     * @return
     */
    public <T> List<T> doQuery(String dsKey, String sql, Object[] objs, Class<T> clazz) {

        dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);

        BeanPropertyRowMapper<T> rowMapper = new BeanPropertyRowMapper<>(clazz);

        List<T> list;

        if (objs == null || objs.length == 0)
            list = jdbcTemplate.query(sql, rowMapper);
        else
            list = jdbcTemplate.query(sql, objs, rowMapper);

        dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        logger.info(sql);

        return list;
    }

    /**
     * 执行查询sql
     *
     * @param dsKey 数据源key
     * @param sql   查询sql
     * @param objs  sql传入参数
     * @return
     */
    public List<Map<String, Object>> doQuery(String dsKey, String sql, Object[] objs) {

        dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);

        RowMapper rowMapper = new ColumnMapRowMapper() {
            @Override
            protected Map<String, Object> createColumnMap(int columnCount) {
                return new HashMap<>(columnCount);
            }
        };
        List<Map<String, Object>> list = null;

        if (objs == null || objs.length == 0)
            list = jdbcTemplate.query(sql, rowMapper);
        else
            list = jdbcTemplate.query(sql, objs, rowMapper);

        dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        logger.info(sql);

        return list;
    }

    /**
     * 执行增、删、改sql
     *
     * @param sql
     * @return
     */
    public int doUpdate(String sql) {
        return doUpdate(sql, new Object[0]);
    }

    /**
     * 执行增、删、改sql
     *
     * @param sql
     * @param objs sql传入参数
     * @return
     */
    public int doUpdate(String sql, Object[] objs) {
        if (objs != null && objs.length > 0)
            return jdbcTemplate.update(sql, objs);
        else
            return jdbcTemplate.update(sql);
    }

    /**
     * 执行增、删、改sql
     *
     * @param dsKey 数据源key
     * @param sql   sql语句
     * @return
     */
    public int doUpdate(String dsKey, String sql) {
        return doUpdate(dsKey, sql, new Object[0]);
    }

    /**
     * 执行增、删、改sql
     *
     * @param dsKey 数据源key
     * @param sql   sql语句
     * @param objs  sql传入参数
     * @return
     */
    public int doUpdate(String dsKey, String sql, Object[] objs) {
        dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);
        int n = 0;
        if (objs != null && objs.length > 0)
            n = jdbcTemplate.update(sql, objs);
        else
            n = jdbcTemplate.update(sql);

        dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        return n;
    }

    /**
     * 批量执行增、删、改sql
     *
     * @param sqls sql语句
     * @return
     */
    @Transactional
    public int[] doUpdate(String[] sqls) {
        return jdbcTemplate.batchUpdate(sqls);
    }

    /**
     * 批量执行个增、删、改sql
     *
     * @param dsKey 数据源key
     * @param sqls  sql语句
     * @return
     */
    public int[] doUpdate(String dsKey, String[] sqls) {
        dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);
        int[] ns = doUpdate(sqls);
        dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        return ns;
    }

    /**
     * 批量执行同一个增、删、改sql
     *
     * @param sql       sql语句
     * @param batchArgs batchArgs.size()表示执行的次数，batchArgs的每个元素表示sql的传入参数
     * @return
     */
    @Transactional
    public int[] doUpdate(String sql, List<Object[]> batchArgs) {
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    /**
     * 批量执行同一个增、删、改sql
     *
     * @param dsKey     数据源key
     * @param sql       sql语句
     * @param batchArgs batchArgs.size()表示执行的次数，batchArgs的每个元素表示sql的传入参数
     * @return
     */
    public int[] doUpdate(String dsKey, String sql, List<Object[]> batchArgs) {
        dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);
        int[] ns = doUpdate(sql, batchArgs);
        dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        return ns;
    }

    /**
     * 执行存储过程
     *
     * @param proc   存储过程名称
     * @param objs   参数
     * @param output 返回结果个数
     * @return
     */
    public List<Object> doProc(String proc, Object[] objs, int output) {
        List<Object> list = jdbcTemplate.execute(con -> {
            //String storedProc = "";// 调用的sql
            String pdCall = "{call " + proc + "(";

            for (int i = 0; i < (objs.length + output - 1); i++) {
                pdCall += "?,";
            }
            pdCall += "?)}";

            CallableStatement cs = con.prepareCall(pdCall);

            return cs;
        }, (CallableStatementCallback<List<Object>>) cs -> {
            //输入参数
            for (int i = 0; i < objs.length; i++) {
                cs.setObject(i + 1, objs[i]);
            }

            // 注册输出参数的类型
            for (int i = 0; i < output; i++) {
                cs.registerOutParameter(objs.length + 1 + i, Types.VARCHAR);
            }

            cs.execute();

            List<Object> list1 = new ArrayList<>();
            for (int i = 0; i < output; i++) {
                list1.add(cs.getObject(objs.length + i + 1));
            }
            return list1;// 获取输出参数的值
        });
        return list;
    }

    /**
     * 执行存储过程
     *
     * @param dsKey  数据源key
     * @param proc   存储过程名称
     * @param objs   参数
     * @param output
     * @return
     */
    public List<Object> doProc(String dsKey, String proc, Object[] objs, int output) {
        dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);
        List<Object> list = doProc(proc, objs, output);
        dynamicDataSourceLookupHelper.releaseDataSourceByKey();
        return list;
    }
}

