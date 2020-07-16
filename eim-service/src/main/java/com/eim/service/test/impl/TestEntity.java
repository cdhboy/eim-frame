package com.eim.service.test.impl;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;


public class TestEntity {

    private String user_id;

    private String user_name;

    private Long user_sysid;

    private String add_time;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getUser_sysid() {
        return user_sysid;
    }

    public void setUser_sysid(Long user_sysid) {
        this.user_sysid = user_sysid;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;

    }
}
