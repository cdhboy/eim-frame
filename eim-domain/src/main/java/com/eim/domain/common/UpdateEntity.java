package com.eim.domain.common;

import java.util.ArrayList;
import java.util.List;

public class UpdateEntity extends BaseEntity{

    private static final long serialVersionUID = 1L;

    private String sql;
    private String[] sqls = new String[0];
    private Object[] objs = new Object[0];
    private List<Object[]> list = new ArrayList<>();

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String[] getSqls() {
        return sqls;
    }

    public void setSqls(String[] sqls) {
        this.sqls = sqls;
    }

    public Object[] getObjs() {
        return objs;
    }

    public void setObjs(Object[] objs) {
        this.objs = objs;
    }

    public List<Object[]> getList() {
        return list;
    }

    public void setList(List<Object[]> list) {
        this.list = list;
    }
}
