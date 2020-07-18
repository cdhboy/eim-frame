package com.eim.dao.helper;

import com.eim.dao.DynamicDataSourceContextHolder;
import com.eim.dao.DynamicRoutingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component("dynamicDataSourceLookupHelper")
public class DynamicDataSourceLookupHelper {
    @Autowired
    @Qualifier("datasource")
    private DynamicRoutingDataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //存储当前线程获取的数据库连接
    private static final ThreadLocal<Connection> connHolder = new ThreadLocal<>();

    /**
     * 根据dataSourceKey获取数据连接
     *
     * @param dataSourceKey
     * @return
     * @throws SQLException
     */
    public Connection getConnection(String dataSourceKey) throws SQLException {
        String key = dataSourceKey;

        if (!DynamicDataSourceContextHolder.containsDataSource(key)) {
            key = "default";
        }

        DynamicDataSourceContextHolder.setDataSourceRouterKey(key);

        connHolder.set(dataSource.getConnection());

        return connHolder.get();
    }

    /**
     * 关闭数据库连接
     *
     * @throws SQLException
     */
    public void closeConnection() throws SQLException {

        if (connHolder.get() != null) {
            connHolder.get().close();
            connHolder.set(null);
        }

        DynamicDataSourceContextHolder.removeDataSourceRouterKey();
    }

    public void changeDataSourceByKey(String dataSourceKey){
        String key = dataSourceKey;

        if (!DynamicDataSourceContextHolder.containsDataSource(key)) {
            key = "default";
        }

        DynamicDataSourceContextHolder.setDataSourceRouterKey(key);
    }

    public void releaseDataSourceByKey(){
        DynamicDataSourceContextHolder.removeDataSourceRouterKey();
    }
}
