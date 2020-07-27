package com.eim.oauth.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private Long userid;
    private String username;
    private String password;
    private Long companyid;
    private Long roleid;
    private Integer active;
    private Integer issuper;

    public Long getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Long getCompanyid() {
        return companyid;
    }

    public Long getRoleid() {
        return roleid;
    }

    public Integer getActive() {
        return active;
    }

    public Integer getIssuper() {
        return issuper;
    }

}
