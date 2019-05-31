package com.lindj.boot.config;

import com.zjdex.framework.util.ResponseUtil;
import com.zjdex.framework.util.data.JsonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author lindj
 * @date 2019/5/31 0031
 * @description
 */
public class MyLogoutFilter extends LogoutFilter {
    private static final Logger logger = LoggerFactory.getLogger(MyLogoutFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        try {


            System.out.println("+++++++++++++++++++++++" + subject.getPrincipal() +
                    "----- " + subject.getPrincipals().getPrimaryPrincipal());
           /* RealmSecurityManager securityManager =
                    (RealmSecurityManager) SecurityUtils.getSecurityManager();
            UserRealm userRealm = (UserRealm)securityManager.getRealms().iterator().next();
            //删除登陆人
            userRealm.getAuthorizationCache().remove(subject.getPrincipal());
            //删除登陆人对应的缓存
            userRealm.getAuthorizationCache().remove(subject.getPrincipals());
            //重新加载subject
            subject.releaseRunAs();
*/
            subject.logout();
        } catch (Exception ex) {
            logger.error("logout error", ex);
        }
        this.writeResult((HttpServletResponse) response);
        //不执行后续的过滤器
        return false;
    }

    private void writeResult(HttpServletResponse response) {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //响应Json结果
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(JsonUtil.objectToJson(ResponseUtil.success()));
        } catch (IOException e) {
            logger.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
