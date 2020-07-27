package com.eim.oauth.mapper;

import com.eim.oauth.entity.Permission;
import com.eim.oauth.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

//    @Results({
//            @Result(property = "userid", column = "user_sysid"),
//            @Result(property = "username", column = "user_id"),
//            @Result(property = "password", column = "user_pwd"),
//            @Result(property = "companyid", column = "company_id"),
//            @Result(property = "roleid", column = "role_id")
//    })
   User getUser(String username);

//    @Results({
//            @Result(property = "permissionname", column = "operate_id")
//    })
    List<Permission> getPermission(long companyid, long roleid, int issuper);
}
