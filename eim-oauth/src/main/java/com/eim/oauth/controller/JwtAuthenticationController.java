package com.eim.oauth.controller;


import com.eim.dao.helper.DynamicDataSourceLookupHelper;
import com.eim.oauth.JwtTokenUtil;
import com.eim.oauth.entity.JwtResponse;
import com.eim.oauth.entity.JwtUser;
import com.eim.oauth.service.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/**
 * JwtAuthenticationController
 * 包含登陆和查看token的方法
 *
 * @author chendh
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private DynamicDataSourceLookupHelper dynamicDataSourceLookupHelper;

    /**
     * 用户名密码登录认证（/auth）
     * @param username
     * @param password
     * @param dsKey
     * @return
     * @throws Exception
     */
//    @PostMapping("${jwt.route.authentication.path}")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
    @RequestMapping("${jwt.route.authentication.path}")
    public ResponseEntity<?> createAuthenticationToken(String username, String password, String dsKey) throws Exception {
        //System.out.println("username:" + authenticationRequest.getUsername() + ",password:" + authenticationRequest.getPassword());

//        //用户名
//        String username = authenticationRequest.getUsername();
//        //密码
//        String password = authenticationRequest.getPassword();

        //用户名、密码登录认证，会调用JwtUserDetailService的loadUserByUsername方法
        //authenticate(username, password);
        if (StringUtils.hasText(dsKey))
            dynamicDataSourceLookupHelper.changeDataSourceByKey(dsKey);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, username + "@" + password));
        if (StringUtils.hasText(dsKey))
            dynamicDataSourceLookupHelper.releaseDataSourceByKey();

        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        //生成token
        final String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        //在head中保存token发回给前端
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("token", token);

        ResponseEntity res = new ResponseEntity(new JwtResponse(token), multiValueMap, HttpStatus.OK);

        return res;
    }

//    private void authenticate(String username, String password) throws Exception {
//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            System.out.println(authentication);
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }

    /**
     * 根据token获取用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/token")
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

}