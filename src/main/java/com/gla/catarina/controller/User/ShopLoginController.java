package com.gla.catarina.controller.User;


import cn.hutool.core.lang.Validator;
import com.gla.catarina.contants.EmailConstants;
import com.gla.catarina.entity.ShopLogin;
import com.gla.catarina.entity.ShopUserInfo;
import com.gla.catarina.entity.ShopUserRole;
import com.gla.catarina.service.IShopLoginService;
import com.gla.catarina.service.IShopUserInfoService;
import com.gla.catarina.service.IShopUserRoleService;
import com.gla.catarina.util.EmailUtils;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.ValidateCode;
import com.gla.catarina.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 登录注册 控制器
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Controller
@Slf4j
public class ShopLoginController {


    @Resource
    private IShopUserRoleService shopUserRoleService;

    @Resource
    private EmailUtils emailUtils;

    /**
     * emailMap
     */
    private static Map<String, String> emailMap = new HashMap<>();
    /**
     * mobileMap
     */
    private static Map<String, String> mobileMap = new HashMap<>();

    @Resource
    private IShopLoginService shopLoginService;

    @Resource
    private IShopUserInfoService shopUserInfoService;


    /**
     * code
     */
    @RequestMapping(value = "/images", method = {RequestMethod.GET, RequestMethod.POST})
    public void imageCode(HttpServletResponse res) throws IOException {
        ValidateCode validateCode = new ValidateCode(818, 200, 5, 80);
        res.setContentType("image/jpeg");
        //禁止图像缓存。
        res.setHeader("Pragma", "no-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", 0);

        validateCode.write(res.getOutputStream());
    }

    /**
     * regCodeSend
     */
    @ResponseBody
    @PostMapping("/user/sendregcode")
    public ResultVo regCodeSend(HttpServletRequest request) throws IOException {
        JSONObject param = EmailUtils.receivePost(request);
        String email = param.getString("userEmail");

        if (!Validator.isEmail(email)) {//判断输入的手机号格式是否正确
            return ResultVo.error("Email is error");
        }
        ShopLogin shopLogin = ShopLogin.builder().email(email).build();
        //是否已存在
        ShopLogin userInfo = shopLoginService.getUserInfo(shopLogin);
        if (null != userInfo) {
            //已存在提示
            return ResultVo.error("Email exist");
        }
        String registerCode = EmailUtils.phonecode();
        JSONObject paramCode = new JSONObject();
        paramCode.put("code", registerCode);
        String result = emailUtils.sendTemplateMail(email, "Register", EmailConstants.PATH_REGISTER_CODE_SEND, paramCode);
        if (null == result) {
            //注册是比对code
            emailMap.put(email, registerCode);
            return ResultVo.ok("Code send success");
        }
        return ResultVo.error("Code send fail");
    }

    /**
     * register
     */
    @ResponseBody
    @PostMapping("/user/register")
    public ResultVo register(@RequestBody ShopUserInfo shopUserInfo, HttpSession session) {
        ShopLogin shopLogin = new ShopLogin();

        shopLogin.setUsername(shopUserInfo.getUsername());
        ShopLogin userInfo = shopLoginService.getUserInfo(shopLogin);
        if (null != userInfo) {
            return ResultVo.error("Username exist");
        }
        //email
        shopLogin.setEmail(shopUserInfo.getEmail());
        userInfo = shopLoginService.getUserInfo(shopLogin);
        if (null != userInfo) {
            return ResultVo.error("Email exist");
        }

        String code = emailMap.get(shopUserInfo.getEmail());
        if (null == code) {
            return ResultVo.error("Register code is lapse");
        }
        if (code.equalsIgnoreCase(shopUserInfo.getVercode())) {
            //salt
            shopLogin.setId(KeyUtil.genUniqueKey()).setUserid(KeyUtil.genUniqueKey())
                    .setMobilephone(shopUserInfo.getMobilephone()).setPassword(new Md5Hash(shopUserInfo.getPassword(), "Campus-shops").toString());
            Integer saveLogin = shopLoginService.saveLogin(shopLogin);
            //新注册用户存入默认头像、存入默认签名
            shopUserInfo.setUserid(shopLogin.getUserid()).setPassword(shopLogin.getPassword()).setUimage("/pic/d1d66c3ea71044a9b938b00859ca94df.jpg").
                    setSign("").setStatus("offline");
            Integer saveUser = shopUserInfoService.saveUser(shopUserInfo);
            if (saveLogin == 1 && saveUser == 1) {
                ShopUserRole role = new ShopUserRole();
                role.setUserid(shopUserInfo.getUserid());
                role.setRoleid(1);
                role.setIdentity("Member");
                shopUserRoleService.saveRole(role);
                session.setAttribute("username", shopUserInfo.getUsername());
                session.setAttribute("userid", shopUserInfo.getUserid());

                return ResultVo.ok();
            }
            return ResultVo.error();
        }
        return ResultVo.error();
    }

    /**
     * login
     */
    @ResponseBody
    @PostMapping("/user/login")
    public ResultVo login(@RequestBody ShopLogin shopLogin, HttpSession session) {
        String passwordSalt = new Md5Hash(shopLogin.getPassword(), "Campus-shops").toString();
        String loginName = shopLogin.getUsername();
        UsernamePasswordToken usernamePasswordToken;

        if (Validator.isMobile((loginName))) {
            usernamePasswordToken = new UsernamePasswordToken(loginName, passwordSalt);
            shopLogin.setMobilephone(loginName);
            shopLogin.setUsername(null);
        } else if (Validator.isEmail(loginName)) {
            usernamePasswordToken = new UsernamePasswordToken(loginName, passwordSalt);
            shopLogin.setEmail(loginName);
            shopLogin.setUsername(null);
        } else {
            usernamePasswordToken = new UsernamePasswordToken(loginName, passwordSalt);
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.login(usernamePasswordToken);
            shopLogin.setPassword(passwordSalt);
            ShopLogin shopLogin1 = shopLoginService.getUserInfo(shopLogin);
            session.setAttribute("username", shopLogin1.getUsername());
            session.setAttribute("userid", shopLogin1.getUserid());
            return ResultVo.ok("Login success");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.error("Error password");
        }
    }

    /**
     * resetPassword
     */
    @ResponseBody
    @PostMapping("/user/sendresetpwd")
    public ResultVo resetPassword(HttpServletRequest request) throws IOException {
        JSONObject param = EmailUtils.receivePost(request);
        String email = param.getString("email");
        Integer type = param.getInt("type");
        ShopLogin shopLogin = new ShopLogin();
        if (type != 1) {
            return ResultVo.error("error");
        }
        if (!Validator.isEmail(email)) {
            return ResultVo.error("Error email");
        }
        //查询手机号是否存在
        shopLogin.setEmail(email);
        ShopLogin userInfo = shopLoginService.getUserInfo(shopLogin);
        if (null == userInfo) {//用户账号不存在
            return ResultVo.error("No user");
        }
        String resetCode = EmailUtils.phonecode();
        JSONObject paramCode = new JSONObject();
        paramCode.put("code", resetCode);
        String result = emailUtils.sendTemplateMail(email, "Register", EmailConstants.PATH_REGISTER_CODE_SEND, paramCode);
        if (null == result) {
            //注册是比对code
            emailMap.put(email, resetCode);
            return ResultVo.ok("Code send success");
        }
        return ResultVo.error("Send code error");
    }

    /**
     * changePwd
     */
    @ResponseBody
    @PostMapping("/user/resetpwd")
    public ResultVo changePwd(@RequestBody ShopLogin shopLogin) {

        ShopLogin shopLogin1 = new ShopLogin();

        String code = emailMap.get(shopLogin.getEmail());

        if (shopLogin.getVercode().equalsIgnoreCase(code)) {
            shopLogin1.setEmail(shopLogin.getEmail());
            ShopLogin userInfo = shopLoginService.getUserInfo(shopLogin1);
            if (null == userInfo) {//用户账号不存在
                return ResultVo.error("No user");
            }

            //盐加密
            String passwordSalt = new Md5Hash(shopLogin.getPassword(), "Campus-shops").toString();

            shopLogin1.setPassword(passwordSalt).setId(userInfo.getId());

            ShopUserInfo shopUserInfo = new ShopUserInfo();
            shopUserInfo.setMobilephone(shopLogin.getMobilephone());
            shopUserInfo.setPassword(passwordSalt);
            shopUserInfo.setUserid(userInfo.getUserid());

            Integer updateLogin = shopLoginService.updateEntity(shopLogin1);
            Integer updateUser = shopUserInfoService.updateEntity(shopUserInfo);
            if (updateLogin == 1 && updateUser == 1) {
                return ResultVo.ok();
            }
            return ResultVo.error();
        }
        return ResultVo.error();
    }

    /**
     * userLogout
     */
    @GetMapping("/user/logout")
    public String userLogout(HttpServletRequest request) {
        HttpSession session1 = request.getSession();
        session1.removeAttribute("userid");
        session1.removeAttribute("username");
        return "redirect:/";
    }


}