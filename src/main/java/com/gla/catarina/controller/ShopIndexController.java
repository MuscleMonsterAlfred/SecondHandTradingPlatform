package com.gla.catarina.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.gla.catarina.util.ServletUtils.getAdminId;

@Controller
public class ShopIndexController {
    /**
     * Home
     * */
    @GetMapping("/")
    public String homeIndex(){
        return "/index";
    }

    /**
     * contacts
     * */
    @GetMapping("/contacts")
    public String contactsUs(){
        return "/common/contacts";
    }

    /**
     * about
     * */
    @GetMapping("/about")
    public String aboutUs(){
        return "/common/about";
    }

    /**
     * indexAdmin
     * */
    @GetMapping("/admin/index")
    public String indexAdmin(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String admin = getAdminId();
        String contextPath = request.getContextPath();
        if (StringUtils.isBlank(admin)){
            response.sendRedirect( contextPath+ "/");//重定向
        }
        return "/admin/index";
    }

    /**
     * userLogin
     * */
    @GetMapping("/login")
    public String userLogin(){
        return "/user/logreg";
    }

    /**
     * passwordForget
     * */
    @GetMapping("/forget")
    public String passwordForget(){
        return "user/forget";
    }

    /**
     * 个人中心
     * */
    @GetMapping("/user/center")
    public String usercenter(){
        return "/user/user-center";
    }

    /**
     * domainHome
     * */
    @GetMapping("/user/home")
    public String domainHome(){
        return "/user/user-home";
    }

    /**
     * changePasswordPre
     * */
    @GetMapping("/user/pass")
    public String changePasswordPre(){
        return "/user/updatepass";
    }

    /**
     * changeUserMobile
     * */
    @GetMapping("/user/phone")
    public String changeUserMobile(){
        return "/user/updatephone";
    }

    /**
     * productList
     * */
    @GetMapping("/user/product")
    public String productList(){
        return "/user/product/productlist";
    }

    /**
     * message
     * */
    @GetMapping("/user/message")
    public String message(){
        return "/user/message/message";
    }
    /**
     * messageAlert
     * */
    @GetMapping("/user/alertmessage")
    public String messageAlert(){
        return "/user/message/alertmessage";
    }
    /**
     * productTopListing
     * */
    @GetMapping("/product-listing")
    public String productTopListing() {
        return "/common/product-listing";
    }

    /**
     * product2Search
     * */
    @GetMapping("/product-search")
    public String product2Search(String keys, ModelMap modelMap) {
        modelMap.put("keys",keys);
        return keys==null?"/error/404":"/common/product-search";
    }

    /**
     * userConsole
     * @return
     */
    @GetMapping("/home/console")
    public String userConsole(){
        return "/admin/home/console";
    }

    /**
     * eCharts
     * */
    @GetMapping("/echars/console")
    public String eCharts(){
        return "/admin/echars/console";
    }


    /**
     * domainMessageIndex
     * @return
     */
    @GetMapping("/app/message/index")
    public String domainMessageIndex(){
        return "/admin/app/message/index";
    }

    /**
     * collect
     * */
    @GetMapping("/user/collect")
    public String collect(){
        return "/user/collect/collectlist";
    }

    /**
     * 用户订单管理
     * */
    @GetMapping("/user/order")
    public String order(){
        return "/user/order/orderecord";
    }

    /**
     * 用户售出记录
     * */
    @GetMapping("/user/sold")
    public String sold(){
        return "/user/sold/soldrecord";
    }

    /**
     * 销量列表
     * */
    @GetMapping("/admin/sold")
    public String adminSold(){
        return "/admin/sold/soldrecord";
    }

    /**
     * 首页公告清单
     * */
    @GetMapping("/user/newslist")
    public String userNews(){
        return "/common/listnews";
    }

    /**
     * 管理员公告列表
     * */
    @GetMapping("/admin/newslist")
    public String adminNews(){
        return "/admin/news/newslist";
    }
}
