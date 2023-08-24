package com.gla.catarina.controller.User;


import cn.hutool.core.lang.UUID;
import com.gla.catarina.entity.ShopLogin;
import com.gla.catarina.entity.ShopUserInfo;
import com.gla.catarina.util.EmailUtils;
import com.gla.catarina.vo.ResultVo;
import net.sf.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.gla.catarina.util.ServletUtils.getUserId;

/**
 * <p>
 * 个人中心 控制器
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Controller
public class ShopUserController {
    @Resource
    private com.gla.catarina.service.IShopLoginService shopLoginService;
    @Resource
    private com.gla.catarina.service.IShopUserInfoService shopUserInfoService;

    @Value("${catarina.env.filePath}")
    private String filePath;

    /**codeMap*/
    private static Map<String, String> codeMap = new HashMap<>();
    /**
     * updatePassword
     */
    @ResponseBody
    @PutMapping("/user/updatepwd")
    public ResultVo updatePassword( HttpServletRequest request) throws IOException {
        JSONObject param = EmailUtils.receivePost(request);
        return getResultVo(param.getString("oldpwd"), param.getString("newpwd"), getUserId());
    }

    private ResultVo getResultVo(String oldPwd, String newPwd, String userId) {
        ShopLogin queryLogin = new ShopLogin();
        ShopUserInfo queryLoginUser = new ShopUserInfo();
        queryLogin.setUserid(userId);

        ShopLogin shopLogin = shopLoginService.getUserInfo(queryLogin);
        String oldPwdSalt = new Md5Hash(oldPwd, "Campus-shops").toString();
        //eq
        if (oldPwdSalt.equals(shopLogin.getPassword())){
            //盐加密
            String passwordSalt = new Md5Hash(newPwd, "Campus-shops").toString();
            shopLogin.setPassword(passwordSalt);
            queryLoginUser.setUserid(shopLogin.getUserid());
            queryLoginUser.setPassword(passwordSalt);
            Integer b1 = shopLoginService.updateEntity(shopLogin);
            Integer b2 = shopUserInfoService.updateEntity(queryLoginUser);
            if (b1 == 1 && b2 == 1) {
                return ResultVo.ok();
            }
            return ResultVo.error();
        }
        return ResultVo.error();
    }

    /**
     * showAvatar
     */
    @ResponseBody
    @PostMapping("/user/avatar")
    public ResultVo showAvatar() {
        return ResultVo.ok(shopUserInfoService.getById(getUserId()));
    }

    /**
     * changeAvatar
     * */
    @PostMapping(value = "/user/updateuimg")
    @ResponseBody
    public JSONObject changeAvatar(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        ShopUserInfo shopUserInfo = new ShopUserInfo();
        shopUserInfo.setUserid(getUserId());


        JSONObject resUrl = new JSONObject();
        String fileName = UUID.fastUUID().toString(true) + "." + FilenameUtils.getExtension(file.getOriginalFilename());//文件全名
        String path = filePath + fileName;
        file.transferTo(new File(path));
        resUrl.put("src", "/pic/"+fileName);
        JSONObject res = new JSONObject();
        res.put("msg", "");
        res.put("code", 0);
        res.put("data", resUrl);
        String url = "/pic/" + fileName;


        shopUserInfo.setUimage(url);
        shopUserInfoService.updateEntity(shopUserInfo);
        return res;
    }

    /**
     * info
     */
    @GetMapping("/user/lookinfo")
    public String info( ModelMap modelMap) {
        modelMap.put("userInfo", shopUserInfoService.getByUserId(getUserId()));
        return "/user/userinfo";
    }

    /**
     * toCom
     */
    @GetMapping("/user/perfectinfo")
    public String toCom(HttpSession session, ModelMap modelMap) {
        modelMap.put("perfectInfo", shopUserInfoService.getByUserId(getUserId()));
        return "/user/perfectInfo";
    }

    /**
     * 修改个人信息
     * 1.前端传入用户昵称（username）、用户邮箱（email）、性别（sex）、学校（school）、院系（faculty）、入学时间（startime）
     * 2.前端传入变更后的字段，未变更的不传入后台
     * 3.判断更改的用户名是否已存在
     * 4.修改个人信息
     */
    @ResponseBody
    @PostMapping("/user/updateinfo")
    public ResultVo updateInfo(@RequestBody ShopUserInfo shopUserInfo) {
        return getResultVo(shopUserInfo);
    }

    private ResultVo getResultVo(ShopUserInfo shopUserInfo) {
        ShopLogin shopLogin = new ShopLogin();
        //
        if (StringUtils.isNotBlank(shopUserInfo.getUsername())){
            shopLogin.setUsername(shopUserInfo.getUsername());
            ShopLogin shopLogin1 = shopLoginService.getUserInfo(shopLogin);
            //
            if (null != shopLogin1){
                return ResultVo.error("Exist");
            }
            shopLogin.setUserid(getUserId());
            //
            shopLoginService.updateEntity(shopLogin);
        }
        shopUserInfo.setUserid(getUserId());
        int b1 = shopUserInfoService.updateEntity(shopUserInfo);
        if (b1 == 1) {
            return ResultVo.ok();
        }
        return ResultVo.error();
    }

}

