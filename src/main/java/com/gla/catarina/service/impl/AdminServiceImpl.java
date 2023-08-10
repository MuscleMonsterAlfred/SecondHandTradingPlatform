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
 * @since 2023-06-21
 */
@Service
public class AdminServiceImpl implements IAdminService {

    @Resource
    private IUserRoleService IUserRoleService;
    @Resource
    private ILoginService ILoginService;
    @Resource
    private IUserInfoService IUserInfoService;
    @Resource
    private ICommodityService ICommodityService;
    @Resource
    private INoticesService INoticesService;

    @Override
    public ResultVo adminlogin(Login login, HttpSession session) {
        String account = login.getUsername();
        String password = login.getPassword();
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
            login.setMobilephone(mobilephone);
            //将封装的login中username变为null
            login.setUsername(null);
            //盐加密
            token = new UsernamePasswordToken(mobilephone, new Md5Hash(password, "Campus-shops").toString());
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            //盐加密
            String passwords = new Md5Hash(password, "Campus-shops").toString();
            login.setPassword(passwords);
            Login login1 = ILoginService.userLogin(login);
            //查询登录者的权限
            Integer roleId = IUserRoleService.LookUserRoleId(login1.getUserid());
            if (roleId == 2 || roleId == 3) {
                session.setAttribute("userid", login1.getUserid());
                session.setAttribute("admin", login1.getUsername());
                session.setAttribute("username", login1.getUsername());
                System.out.println("userid：" + login1.getId());
                System.out.println("admin：" + login1.getUsername());
                System.out.println("username：" + login1.getUsername());
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
        List<UserInfo> userInfoList = IUserInfoService.queryAllUserInfo((page - 1) * limit, limit, roleid, userstatus);
        Integer dataNumber = IUserInfoService.queryAllUserCount(roleid);
        return new LayuiPageVo("", 0, dataNumber, userInfoList);
    }

    @Override
    public ResultVo setadmin(String userid, Integer roleid) {
        if (roleid == 2) {
            Integer i = ILoginService.updateLogin(new Login().setUserid(userid).setRoleid(roleid));
            if (i == 1) {
                IUserRoleService.UpdateUserRole(new UserRole().setUserid(userid).setRoleid(2).setIdentity("网站管理员"));
                return new ResultVo(true, StatusCode.OK, "success");
            }
            return new ResultVo(true, StatusCode.ERROR, "error");
        } else if (roleid == 1) {
            Integer i = ILoginService.updateLogin(new Login().setUserid(userid).setRoleid(roleid));
            if (i == 1) {
                IUserRoleService.UpdateUserRole(new UserRole().setUserid(userid).setRoleid(1).setIdentity("网站用户"));
                return new ResultVo(true, StatusCode.OK, "success");
            }
            return new ResultVo(true, StatusCode.ERROR, "error");
        }
        return new ResultVo(false, StatusCode.ACCESSERROR, "error");
    }

    @Override
    public ResultVo adminuserlist(String userid, Integer userstatus) {
        if (userstatus == 0) {
            Integer i = ILoginService.updateLogin(new Login().setUserid(userid).setUserstatus(userstatus));
            Integer j = IUserInfoService.UpdateUserInfo(new UserInfo().setUserid(userid).setUserstatus(userstatus));
            if (i == 1 && j == 1) {
                return new ResultVo(true, StatusCode.OK, "success");
            }
            return new ResultVo(true, StatusCode.ERROR, "error");
        } else if (userstatus == 1) {
            Integer i = ILoginService.updateLogin(new Login().setUserid(userid).setUserstatus(userstatus));
            Integer j = IUserInfoService.UpdateUserInfo(new UserInfo().setUserid(userid).setUserstatus(userstatus));
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
            List<Commodity> commodityList = ICommodityService.queryAllCommodity((page - 1) * limit, limit, null, null);
            Integer dataNumber = ICommodityService.queryCommodityCount(null, null);
            return new LayuiPageVo("", 0, dataNumber, commodityList);
        } else {
            List<Commodity> commodityList = ICommodityService.queryAllCommodity((page - 1) * limit, limit, null, commstatus);
            Integer dataNumber = ICommodityService.queryCommodityCount(null, commstatus);
            return new LayuiPageVo("", 0, dataNumber, commodityList);
        }
    }

    @Override
    public ResultVo changeCommstatus(String commid, Integer commstatus) {
        Integer i = ICommodityService.ChangeCommstatus(commid, commstatus);
        if (i == 1) {
            /**发出Wait Approve结果的系统通知*/
            Commodity commodity = ICommodityService.LookCommodity(new Commodity().setCommid(commid));
            if (commstatus == 0) {
                Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(commodity.getUserid()).setTpname("Wait Approve")
                        .setWhys("Your product has not been approved and currently does not support public release <a href=/product-detail/" + commodity.getCommid() + " style=\"color:#08bf91\" target=\"_blank\" >" + commodity.getCommname() + "</a> ");
                INoticesService.insertNotices(notices);
            } else if (commstatus == 1) {
                Notices notices = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(commodity.getUserid()).setTpname("Wait Approve")
                        .setWhys("Your product has been approved, go and take a look now <a href=/product-detail/" + commodity.getCommid() + " style=\"color:#08bf91\" target=\"_blank\" >" + commodity.getCommname() + "</a> ");
                INoticesService.insertNotices(notices);
            }
            return new ResultVo(true, StatusCode.OK, "success");
        }
        return new ResultVo(false, StatusCode.ERROR, "error");
    }

}
