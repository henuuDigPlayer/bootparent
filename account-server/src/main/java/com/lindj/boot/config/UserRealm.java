package com.lindj.boot.config;

import com.lindj.boot.model.SysRole;
import com.lindj.boot.model.SysUser;
import com.lindj.boot.service.SysRoleService;
import com.lindj.boot.service.SysUserService;
import com.lindj.boot.util.JwtUtil;
import com.lindj.boot.util.SecurityConsts;
import com.zjdex.framework.util.data.JsonUtil;
import com.zjdex.framework.util.data.StringUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: hxy
 * @description: 自定义Realm
 * @date: 2017/10/24 10:06
 */
@Service
public class UserRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService roleService;
    @Autowired
    private RedissonClient redissonClient;


    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("lindj");
        String username = JwtUtil.getClaim(principals.toString(), SecurityConsts.ACCOUNT);
        SysUser user = sysUserService.getUserByUsername(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<String> roleList =
                this.roleService.selectListByUid(user.getId()).stream().map(SysRole::getRoleName).collect(Collectors.toList());
        System.out.println("roleList: "+JsonUtil.objectToJson(roleList));
        simpleAuthorizationInfo.addRoles(roleList);
        List<String> permissionList = this.sysUserService.getUserPermissionList(user.getId());
        System.out.println("permissionList: "+JsonUtil.objectToJson(permissionList));
        simpleAuthorizationInfo.addStringPermissions(permissionList);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * 验证当前登录的Subject，LoginController.login()方法中执行Subject.login()时 执行此方法
     *
     * @param authcToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        String token = (String) authcToken.getCredentials();
        logger.info("doGetAuthenticationInfo:{}", token);
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getClaim(token, SecurityConsts.ACCOUNT);
        if (username == null) {
            throw new AuthenticationException("token invalid");
        }
        SysUser sysUser = sysUserService.getUserByUsername(username);
        logger.info("sysUser:{}", JsonUtil.objectToJson(sysUser));
        if (sysUser == null) {
            System.out.println("User didn't existed!");
            throw new AuthenticationException("User didn't existed!");
        }

        // 校验jwt是否正确
        if (!JwtUtil.verify(token, sysUser.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }
        // 获取AccessToken时间戳，与RefreshToken的时间戳对比
        String refreshTokenCacheKey = SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + username;
        RBucket<String> bucket = this.redissonClient.getBucket(refreshTokenCacheKey);
        String currentTimeMillisRedis = bucket.get();
        boolean value = bucket == null || StringUtil.isEmpty(currentTimeMillisRedis);
        if (value || !JwtUtil.getClaim(token, SecurityConsts.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
            throw new AuthenticationException("Token expired or incorrect.");
        }
        return new SimpleAuthenticationInfo(token, token, "user_realm");
    }


}
