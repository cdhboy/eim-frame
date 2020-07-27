package com.eim.oauth;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败的异常处理
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        //int code = HttpServletResponse.SC_UNAUTHORIZED;
        String error = authException.toString();

        if ( authException instanceof UsernameNotFoundException) {
            //code = HttpServletResponse.SC_
            error = "账户名或者密码错误!";
        }else if (authException instanceof BadCredentialsException) {
            error ="账户名或者密码错误!";
        } else if (authException instanceof LockedException) {
            error = "账户被锁定";
        } else if (authException instanceof CredentialsExpiredException) {
            error = "认证过期";
        } else if (authException instanceof AccountExpiredException) {
            error = "账户过期";
        } else if (authException instanceof DisabledException) {
            error ="账户被禁用";
        }
        /*else {
            error ="登录失败!";
        }*/
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/plain;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(error);
        response.getWriter().flush();
    }
}