package com.gla.catarina.controller.admin;

import com.gla.catarina.entity.ShopLogin;
import com.gla.catarina.service.IShopAdminService;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author: catarina
 * @Description: 管理员控制器
 * @Date: 2023/3/10 16:54
 */
@Controller
public class ShopAdminController {

    @Resource
    private IShopAdminService adminService;

    @GetMapping("/admin")
    public String admin() {
        return "admin/login/login";
    }

    @ResponseBody
    @PostMapping("/admin/login")
    public ResultVo loginAdmin(@RequestBody ShopLogin shopLogin, HttpSession session) {
        return adminService.adminlogin(shopLogin, session);
    }

    @GetMapping("/admin/userlist")
    public String listUser() {
        return "/admin/user/userlist";
    }

    @RequiresPermissions("admin:set")
    @GetMapping("/admin/adminlist")
    public String listAdmin() {
        return "/admin/user/adminlist";
    }

    @GetMapping("/admin/userlist/{roleid}/{userstatus}")
    @ResponseBody
    public LayuiPageVo listUserData(int limit, int page, @PathVariable("roleid") Integer roleid, @PathVariable("userstatus") Integer userstatus) {
        return adminService.userlist(limit, page, roleid, userstatus);
    }

    @PutMapping("/admin/set/administrator/{userid}/{roleid}")
    @ResponseBody
    public ResultVo adminSave(@PathVariable("userid") String userid, @PathVariable("roleid") Integer roleid) {
        return adminService.setadmin(userid, roleid);
    }

    @PutMapping("/admin/user/forbid/{userid}/{userstatus}")
    @ResponseBody
    public ResultVo listAdminData(@PathVariable("userid") String userid, @PathVariable("userstatus") Integer userstatus) {
        return adminService.adminuserlist(userid, userstatus);
    }

    @GetMapping("/admin/product")
    public String product() {
        return "/admin/product/productlist";
    }

    @GetMapping("/admin/commodity/{commstatus}")
    @ResponseBody
    public LayuiPageVo queryUserProduct(@PathVariable("commstatus") Integer commstatus, int limit, int page) {
        return adminService.userCommodity(commstatus, limit, page);
    }

    @ResponseBody
    @PutMapping("/admin/changecommstatus/{commid}/{commstatus}")
    public ResultVo updateProductStatus(@PathVariable("commid") String commid, @PathVariable("commstatus") Integer commstatus) {
        return adminService.changeCommstatus(commid, commstatus);
    }

}
