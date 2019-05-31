package com.lindj.boot.config;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.lindj.boot.service.SysUserService;
import com.lindj.boot.util.JwtUtil;
import com.lindj.boot.util.SecurityConsts;
import com.zjdex.framework.util.data.StringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lindj
 * @date 2019/5/29 0029
 * @description
 */
public class TokenFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private RedissonClient redissonClient;

    private JwtProperties jwtProperties;
    private SysUserService userService;

    public TokenFilter(RedissonClient redissonClient, JwtProperties jwtProperties,
                       SysUserService userService){
        this.redissonClient = redissonClient;
        this.jwtProperties = jwtProperties;
        this.userService = userService;
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");

        logger.info("登陆了：" + authorization);
        return authorization != null;
    }


    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean value = isLoginAttempt(request, response);
        if (value) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                String msg = e.getMessage();
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
                    System.out.println(msg);
                } else if (throwable != null && throwable instanceof TokenExpiredException) {
                    System.out.println("刷新token");
                    // AccessToken已过期
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        msg = "Token已过期(" + throwable.getMessage() + ")";
                        System.out.println(msg);
                    }
                } else {
                    if (throwable != null) {
                        msg = throwable.getMessage();
                    }
                }
                response(request, response, msg);
                return false;
            }
        }
        logger.info("isAccessAllowed:{}", value);
        return true;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) {
        response(request, response, "");
        return false;
    }


    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");

        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        logger.info("executeLogin");
        return true;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /401
     */
    private void response(ServletRequest req, ServletResponse resp, String message) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("code", HttpStatus.FORBIDDEN.value());
            result.put("mesage", message);
            out.append(JSON.toJSONString(result));
        } catch (IOException e) {
            logger.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        // 获取AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        // 获取当前Token的帐号信息
        String account = JwtUtil.getClaim(token, SecurityConsts.ACCOUNT);

        System.out.println(token);
        System.out.println(account);

        String refreshTokenCacheKey = SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        RBucket<String> bucket = redissonClient.getBucket(refreshTokenCacheKey);
        // 判断Redis中RefreshToken是否存在
        if (bucket != null && !StringUtil.isEmpty(bucket.get())) {
            // 获取RefreshToken时间戳,及AccessToken中的时间戳
            // 相比如果一致，进行AccessToken刷新
            String currentTimeMillisRedis = bucket.get();
            String tokenMillis = JwtUtil.getClaim(token, SecurityConsts.CURRENT_TIME_MILLIS);
            System.out.println(currentTimeMillisRedis + "+++++++++" + tokenMillis);
            if (tokenMillis.equals(currentTimeMillisRedis)) {

                // 设置RefreshToken中的时间戳为当前最新时间戳
                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
                Integer refreshTokenExpireTime = this.jwtProperties.getRefreshTokenExpireTime();
                bucket.set(currentTimeMillis, refreshTokenExpireTime * 60 * 1000L, TimeUnit.MILLISECONDS);
                System.out.println("设置refreshToken过期时间：" + currentTimeMillis);
                // 刷新AccessToken，为当前最新时间戳
                token = JwtUtil.sign(account,
                        userService.getUserByUsername(account).getPassword(), currentTimeMillis,
                        this.jwtProperties.getTokenExpireTime() * 60 * 1000L);

                System.out.println("生成新tokren：" + token);

                // 使用AccessToken 再次提交给ShiroRealm进行认证，如果没有抛出异常则登入成功，返回true
                JwtToken jwtToken = new JwtToken(token);
                this.getSubject(request, response).login(jwtToken);

                // 设置响应的Header头新Token
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);
                return true;
            }
        }
        System.out.println("refresh time");
        return false;
    }

}
