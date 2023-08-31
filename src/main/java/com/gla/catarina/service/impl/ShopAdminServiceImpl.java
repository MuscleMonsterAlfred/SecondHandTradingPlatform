package com.gla.catarina.service.impl;

import com.gla.catarina.entity.*;
import com.gla.catarina.service.*;
import com.gla.catarina.util.*;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author catarina
 */
@Service
public class ShopAdminServiceImpl implements IShopAdminService {

    @Resource
    private IShopUserRoleService IShopUserRoleService;
    @Resource
    private IShopLoginService IShopLoginService;
    @Resource
    private IShopUserInfoService IShopUserInfoService;
    @Resource
    private IShopCommodityService IShopCommodityService;
    @Resource
    private IShopNoticesService IShopNoticesService;

    @Override
    public ResultVo adminlogin(ShopLogin shopLogin, HttpSession session) {
        String account = shopLogin.getUsername();
        String password = shopLogin.getPassword();
        UsernamePasswordToken token;
        //判断输入的账号是否手机号
        if (!EmailUtils.justPhone(account)) {
            //输入的是用户名
            String username = account;
            //盐加密
            token = new UsernamePasswordToken(username, new Md5Hash(password, "Campus-shops").toString());
        } else {
            //输入的是手机号
            String mobilephone = account;
            shopLogin.setMobilephone(mobilephone);
            //将封装的login中username变为null
            shopLogin.setUsername(null);
            //盐加密
            token = new UsernamePasswordToken(mobilephone, new Md5Hash(password, "Campus-shops").toString());
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            //盐加密
            String passwords = new Md5Hash(password, "Campus-shops").toString();
            shopLogin.setPassword(passwords);
            ShopLogin shopLogin1 = IShopLoginService.getUserInfo(shopLogin);
            //查询登录者的权限
            Integer roleId = IShopUserRoleService.getRoleId(shopLogin1.getUserid());
            if (roleId == 2 || roleId == 3) {
                session.setAttribute("userid", shopLogin1.getUserid());
                session.setAttribute("admin", shopLogin1.getUsername());
                session.setAttribute("username", shopLogin1.getUsername());
                System.out.println("userid：" + shopLogin1.getId());
                System.out.println("admin：" + shopLogin1.getUsername());
                System.out.println("username：" + shopLogin1.getUsername());
                return new ResultVo(true, StatusCode.OK, "Login success");
            }
            return new ResultVo(true, StatusCode.ACCESSERROR, "No Perm");
        } catch (UnknownAccountException e) {
            return new ResultVo(true, StatusCode.LOGINERROR, "No account");
        } catch (IncorrectCredentialsException e) {
            return new ResultVo(true, StatusCode.LOGINERROR, "Error password");
        }
    }

    @Override
    public LayuiPageVo userlist(int limit, int page, Integer roleid, Integer userstatus) {
        List<ShopUserInfo> shopUserInfoList = IShopUserInfoService.queryAllUserInfo((page - 1) * limit, limit, roleid, userstatus);
        Integer dataNumber = IShopUserInfoService.queryAllUserCount(roleid);
        return new LayuiPageVo("", 0, dataNumber, shopUserInfoList);
    }

    @Override
    public ResultVo setadmin(String userid, Integer roleid) {
        if (roleid == 2) {
            Integer i = IShopLoginService.updateEntity(new ShopLogin().setUserid(userid).setRoleid(roleid));
            if (i == 1) {
                IShopUserRoleService.UpdateUserRole(new ShopUserRole().setUserid(userid).setRoleid(2).setIdentity("网站管理员"));
                return new ResultVo(true, StatusCode.OK, "success");
            }
            return new ResultVo(true, StatusCode.ERROR, "error");
        } else if (roleid == 1) {
            Integer i = IShopLoginService.updateEntity(new ShopLogin().setUserid(userid).setRoleid(roleid));
            if (i == 1) {
                IShopUserRoleService.UpdateUserRole(new ShopUserRole().setUserid(userid).setRoleid(1).setIdentity("网站用户"));
                return new ResultVo(true, StatusCode.OK, "success");
            }
            return new ResultVo(true, StatusCode.ERROR, "error");
        }
        return new ResultVo(false, StatusCode.ACCESSERROR, "error");
    }

    @Override
    public ResultVo adminuserlist(String userid, Integer userstatus) {
        if (userstatus == 0) {
            Integer i = IShopLoginService.updateEntity(new ShopLogin().setUserid(userid).setUserstatus(userstatus));
            Integer j = IShopUserInfoService.updateEntity(new ShopUserInfo().setUserid(userid).setUserstatus(userstatus));
            if (i == 1 && j == 1) {
                return new ResultVo(true, StatusCode.OK, "success");
            }
            return new ResultVo(true, StatusCode.ERROR, "error");
        } else if (userstatus == 1) {
            Integer i = IShopLoginService.updateEntity(new ShopLogin().setUserid(userid).setUserstatus(userstatus));
            Integer j = IShopUserInfoService.updateEntity(new ShopUserInfo().setUserid(userid).setUserstatus(userstatus));
            if (i == 1 && j == 1) {
                return new ResultVo(true, StatusCode.OK, "success");
            }
            return new ResultVo(true, StatusCode.ERROR, "error");
        }
        return new ResultVo(false, StatusCode.ACCESSERROR, "error");
    }


    @Override
    public LayuiPageVo userCommodity(Integer commstatus, int limit, int page) {
        if (commstatus == 100) {
            List<ShopCommodity> shopCommodityList = IShopCommodityService.listByUserStatus((page - 1) * limit, limit, null, null);
            Integer dataNumber = IShopCommodityService.countByUserStatus(null, null);
            return new LayuiPageVo("", 0, dataNumber, shopCommodityList);
        } else {
            List<ShopCommodity> shopCommodityList = IShopCommodityService.listByUserStatus((page - 1) * limit, limit, null, commstatus);
            Integer dataNumber = IShopCommodityService.countByUserStatus(null, commstatus);
            return new LayuiPageVo("", 0, dataNumber, shopCommodityList);
        }
    }

    @Override
    public ResultVo changeCommstatus(String commid, Integer commstatus) {
        Integer i = IShopCommodityService.updateStatus(commid, commstatus);
        if (i == 1) {
            /**发出Wait Approve结果的系统通知*/
            ShopCommodity shopCommodity = IShopCommodityService.getById(commid);
            if (commstatus == 0) {
                ShopNotices shopNotices = new ShopNotices().setId(KeyUtil.genUniqueKey()).setUserid(shopCommodity.getUserid()).setTpname("Wait Approve")
                        .setWhys("Your product has not been approved and currently does not support public release <a href=/product-detail/" + shopCommodity.getCommid() + " style=\"color:#08bf91\" target=\"_blank\" >" + shopCommodity.getCommname() + "</a> ");
                IShopNoticesService.saveEntity(shopNotices);
            } else if (commstatus == 1) {
                ShopNotices shopNotices = new ShopNotices().setId(KeyUtil.genUniqueKey()).setUserid(shopCommodity.getUserid()).setTpname("Wait Approve")
                        .setWhys("Your product has been approved, go and take a look now <a href=/product-detail/" + shopCommodity.getCommid() + " style=\"color:#08bf91\" target=\"_blank\" >" + shopCommodity.getCommname() + "</a> ");
                IShopNoticesService.saveEntity(shopNotices);
            }
            return new ResultVo(true, StatusCode.OK, "success");
        }
        return new ResultVo(false, StatusCode.ERROR, "error");
    }

}
