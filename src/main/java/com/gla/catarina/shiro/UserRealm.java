package com.gla.catarina.shiro;

import cn.hutool.core.lang.Validator;
import com.gla.catarina.service.ILoginService;
import com.gla.catarina.service.IUserPermService;
import com.gla.catarina.service.IUserRoleService;
import com.gla.catarina.entity.Login;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;

import java.util.List;

/**
 * 自动与i的Realm
 */

public class UserRealm extends AuthorizingRealm {
    @Resource
    private IUserPermService userPermsService;
    @Resource
    private IUserRoleService IUserRoleService;
    @Resource
    private ILoginService ILoginService;

    /**
     * 执行授权逻辑
     * 只要访问加上授权的资源都会调用改方法
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //到数据库查询当前用户的授权的字符串
        Subject subject = SecurityUtils.getSubject();
        Login login = (Login) subject.getPrincipal();
        Integer permId = IUserRoleService.LookUserRoleId(login.getUserid());
        List<String> userPerms = userPermsService.LookPermsByUserid(permId);
        info.addStringPermissions(userPerms);
        return info;
    }

    /**
     * 执行认证逻辑
     * 只要使用subject.login(token) 就会调用该方法
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //编写shiro判断逻辑，判断用户名和密码
        //1、判断用户名
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        Login login = new Login();
        //如果传入的是用户名
        if (Validator.isMobile(username)) {
            //如果传入的是手机号
            login.setMobilephone(username);

        } else if (Validator.isEmail(username)) {
            login.setEmail(username);
        } else {
            login.setUsername(username);
        }
        Login Login1 = ILoginService.userLogin(login);
        if (Login1 == null) {
            //用户不存在
            return null;//shiro底层抛出UnknownAccountException
        }
        //2、判断密码 三个参数：1、返回给subject.login(token);方法的参数  2、数据库中的密码 3、shiro的名字
        return new SimpleAuthenticationInfo(Login1, Login1.getPassword(), "");
    }
}
