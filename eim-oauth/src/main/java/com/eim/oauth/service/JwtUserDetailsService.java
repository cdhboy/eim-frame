package com.eim.oauth.service;

import com.eim.oauth.entity.JwtUser;
import com.eim.oauth.entity.Permission;
import com.eim.oauth.entity.User;
import com.eim.oauth.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * JwtUserDetailsService
 * 实现UserDetailsService,重写loadUserByUsername方法
 *
 * @author chendh
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userMapper.getUser(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }

       // String password = new EimPasswordEncoder().encode(username + "@" + user.getPassword());

        List<String> grantedAuthorities = new ArrayList<>();

        List<Permission> list = userMapper.getPermission(user.getCompanyid(), user.getRoleid(), user.getIssuper());

        for (Permission permission : list) {
            if (permission != null && permission.getPermissionname() != null) {
                grantedAuthorities.add(permission.getPermissionname());
            }
        }

        return new JwtUser(String.valueOf(user.getUserid()), user.getUsername(), user.getPassword(), grantedAuthorities, user.getActive(),user);
    }
}