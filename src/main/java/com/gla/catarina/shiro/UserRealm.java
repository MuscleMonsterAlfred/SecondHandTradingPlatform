package com.gla.catarina.shiro;


import cn.hutool.core.lang.Validator;
import com.gla.catarina.entity.ShopLogin;
import com.gla.catarina.service.IShopUserPermService;
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
    private IShopUserPermService userPermsService;
    @Resource
    private com.gla.catarina.service.IShopUserRoleService shopUserRoleService;
    @Resource
    private com.gla.catarina.service.IShopLoginService shopLoginService;

    /**
     * AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Subject subject = SecurityUtils.getSubject();
        ShopLogin shopLogin = (ShopLogin) subject.getPrincipal();
        List<String> userPerms = userPermsService.LookPermsByUserid(shopUserRoleService.getRoleId(shopLogin.getUserid()));
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermissions(userPerms);
        return info;
    }

    /**
     * AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        ShopLogin query = new ShopLogin();
        String username = token.getUsername();
        //email
        if (Validator.isEmail(username)) {
            query.setEmail(username);
        } else if (Validator.isMobile(username)) {
            query.setMobilephone(username);
        } else {
            query.setUsername(username);
        }
        ShopLogin shopLogin = shopLoginService.getUserInfo(query);
        if (null == shopLogin) {
            return null;
        }
        //2、判断密码 三个参数：1、返回给subject.login(token);方法的参数  2、数据库中的密码 3、shiro的名字
        return new SimpleAuthenticationInfo(shopLogin, shopLogin.getPassword(), "");
    }
}
