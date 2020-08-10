package com.eim.domain.common;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public class ResultEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //0:成功，1:失败
    private String code;
    private String desc;
    private Object[] data;

    public ResultEntity(String code, String desc, Object[] data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    public static ResultEntity ok(Object[] data) {
        return new ResultEntity("0", "", data);
    }

    public static ResultEntity fail(String desc) {
        return fail(desc, null);
    }

    public static ResultEntity fail(String desc, Object[] data) {
        return new ResultEntity("1", desc, data);
    }

    public static ResultEntity fail(Throwable err) {
        StringWriter sw = new StringWriter();
        err.printStackTrace(new PrintWriter(sw, true));
        sw.flush();

        return fail(sw.toString());
    }
}
