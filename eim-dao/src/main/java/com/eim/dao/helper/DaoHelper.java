package com.eim.dao.helper;

import com.eim.dao.DynamicRoutingDataSource;
import com.eim.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoHelper<T> {

    private final String NAME = "dynamicDataSourceLookupHelper";

    private DynamicDataSourceLookupHelper dynamicDataSourceLookupHelper;

    private Class<T> clazz;

    public DaoHelper(Class<T> clazz) {
        dynamicDataSourceLookupHelper = SpringContextUtil.getBean(NAME, DynamicDataSourceLookupHelper.class);

        this.clazz = clazz;

    }

    /**
     * 执行查询sql
     *
     * @param dsKey 数据库key
     * @param sql
     * @return
     */
    public List<T> doQuery(String dsKey, String sql) {
        return doQuery(dsKey, sql, null);
    }

    /**
     * 执行查询sql
     *
     * @param dsKey    数据库key
     * @param sql
     * @param paraList
     * @return
     */
    public List<T> doQuery(String dsKey, String sql, List<Object> paraList) {
        // String errorMsg = "";
        // long time = System.currentTimeMillis();
        Connection conn = null;
        List<T> list = new ArrayList<>();
        try {
            conn = dynamicDataSourceLookupHelper.getConnection(dsKey);
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql);
            // 传入变量参数
            if (paraList != null) {
                for (int i = 0; i < paraList.size(); i++)
                    ps.setObject(i + 1, paraList.get(i));
            }
            ResultSet result = ps.executeQuery();

            list = DataFormatHelper.format(result, clazz);

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (Exception e1) {
                }
            }
        } finally {
            try {
                dynamicDataSourceLookupHelper.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return list;
    }
}

