package com.gla.catarina.controller.admin;

import com.gla.catarina.entity.*;
import com.gla.catarina.service.*;
import com.gla.catarina.util.JustPhone;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.util.ValidateCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author: catarina
 * @Description: 管理员控制器
 * @Date: 2023/3/10 16:54
 */
@Controller
public class AdminController {

    @Resource
    private AdminService adminService;

    @GetMapping("/admin")
    public String admintologin() {
        return "admin/login/login";
    }

    @ResponseBody
    @PostMapping("/admin/login")
    public ResultVo adminlogin(@RequestBody Login login, HttpSession session) {
        return adminService.adminlogin(login, session);
    }

    @GetMapping("/admin/userlist")
    public String userlist() {
        return "/admin/user/userlist";
    }

    @RequiresPermissions("admin:set")
    @GetMapping("/admin/adminlist")
    public String adminlist() {
        return "/admin/user/adminlist";
    }

    @GetMapping("/admin/userlist/{roleid}/{userstatus}")
    @ResponseBody
    public LayuiPageVo userlist(int limit, int page, @PathVariable("roleid") Integer roleid, @PathVariable("userstatus") Integer userstatus) {
        return adminService.userlist(limit, page, roleid, userstatus);
    }

    @PutMapping("/admin/set/administrator/{userid}/{roleid}")
    @ResponseBody
    public ResultVo setadmin(@PathVariable("userid") String userid, @PathVariable("roleid") Integer roleid) {
        return adminService.setadmin(userid, roleid);
    }

    @PutMapping("/admin/user/forbid/{userid}/{userstatus}")
    @ResponseBody
    public ResultVo adminuserlist(@PathVariable("userid") String userid, @PathVariable("userstatus") Integer userstatus) {
        return adminService.adminuserlist(userid, userstatus);
    }

    @GetMapping("/admin/product")
    public String adminproduct() {
        return "/admin/product/productlist";
    }

    @GetMapping("/admin/commodity/{commstatus}")
    @ResponseBody
    public LayuiPageVo userCommodity(@PathVariable("commstatus") Integer commstatus, int limit, int page) {
        return adminService.userCommodity(commstatus, limit, page);
    }

    @ResponseBody
    @PutMapping("/admin/changecommstatus/{commid}/{commstatus}")
    public ResultVo changeCommstatus(@PathVariable("commid") String commid, @PathVariable("commstatus") Integer commstatus) {
        return adminService.changeCommstatus(commid, commstatus);
    }

}
