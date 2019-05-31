package com.lindj.boot.controller;

import com.lindj.boot.config.JwtProperties;
import com.lindj.boot.model.SysUser;
import com.lindj.boot.service.SysUserService;
import com.lindj.boot.util.JwtUtil;
import com.lindj.boot.util.ResponseBean;
import com.lindj.boot.util.SecurityConsts;
import com.lindj.boot.util.UnauthorizedException;
import com.zjdex.framework.holder.ResponseHolder;
import com.zjdex.framework.util.data.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * @author lindj
 * @date 2019/5/29 0029
 * @description
 */
@RestController
@RequestMapping("/user")
public class AccountController {

    @Autowired
    private SysUserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private RedissonClient redissonClient;


    @PostMapping("/login")
    public Object login(@RequestParam("username") String username,
                              @RequestParam("password") String password) {
        SysUser sysUser = userService.getUserByUsername(username);
        if (sysUser.getPassword().equals(password)) {
            return loginSuccess(username, password);
        } else {
            throw new UnauthorizedException();
        }
    }

    @PostMapping("/test")
    @RequiresPermissions("user:test")
    public Object test() {
        return new ResponseBean(200, "Login success", "123123");
    }

    @PostMapping("/logout")
    public Object logout() {
        return new ResponseBean(200, "Login success", "123123");
    }

    /**
     * 登录后更新缓存，生成token，设置响应头部信息
     * @param account
     */
    private ResponseBean loginSuccess(String account, String password){
        HttpServletResponse response = ResponseHolder.getResponse();
        String currentTimeMillis = String.valueOf(System.currentTimeMillis());

        // 清除可能存在的Shiro权限信息缓存
        String tokenKey= SecurityConsts.PREFIX_SHIRO_CACHE + account;
        RBucket rightBucket = redissonClient.getBucket(tokenKey);
        if (rightBucket != null) {
            rightBucket.delete();
        }
        //更新RefreshToken缓存的时间戳
        String refreshTokenKey= SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        RBucket refreshBucket = redissonClient.getBucket(refreshTokenKey);
        if (refreshBucket != null && refreshBucket.get() != null) {
            refreshBucket.set(currentTimeMillis,
                    jwtProperties.getRefreshTokenExpireTime()* 60 * 1000L, TimeUnit.MILLISECONDS);
        }else{
            refreshBucket.set(currentTimeMillis,
                    jwtProperties.getRefreshTokenExpireTime() * 60 * 1000L, TimeUnit.MILLISECONDS);
        }
        System.out.println(account + "-----------------" + password + "--------------------------" + currentTimeMillis);
        String token = JwtUtil.sign(account, password, currentTimeMillis,
                jwtProperties.getTokenExpireTime() * 60 * 1000L);
        //写入header
        response.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, token);
        response.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);

        return new ResponseBean(200, "Login success", token);
    }


}
