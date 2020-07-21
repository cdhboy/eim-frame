package com.eim.oauth.mapper;

import com.eim.oauth.entity.Permission;
import com.eim.oauth.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;

import java.util.List;

@Mapper
public interface UserMapper {

    @Results({
            @Result(property = "userid", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "enabled", column = "active"),
    })

    User getUser(String username);

    List<Permission> getPermission(Long user_id);
}
