package com.eim.oauth.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Permission implements Serializable {
    private String permissionname;
    private String permissionurl;

    public String getPermissionname() {
        return permissionname;
    }

    public void setPermissionname(String permissionname) {
        this.permissionname = permissionname;
    }

    public String getPermissionurl() {
        return permissionurl;
    }

    public void setPermissionurl(String permissionurl) {
        this.permissionurl = permissionurl;
    }
}
