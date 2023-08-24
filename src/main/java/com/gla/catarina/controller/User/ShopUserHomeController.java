package com.gla.catarina.controller.User;


import com.gla.catarina.entity.ShopCommodity;
import com.gla.catarina.entity.ShopUserInfo;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: catarina
 * @Date: 2023/2/23 18:07
 */

@Controller
public class ShopUserHomeController {
    @Resource
    private com.gla.catarina.service.IShopUserInfoService shopUserInfoService;
    @Resource
    private com.gla.catarina.service.IShopCommodityService shopCommodityService;

    /**
     * info
     */
    @ResponseBody
    @GetMapping("/user/userinfo/{userid}")
    public ResultVo info(@PathVariable("userid") String userid) {
        ShopUserInfo shopUserInfo = shopUserInfoService.getByUserId(userid);
        if (null != shopUserInfo){
            return ResultVo.ok(shopUserInfo);
        }
        return ResultVo.error();
    }

    /**
     * myCommodity
     */
    @ResponseBody
    @GetMapping("/user/usercommodity/{userid}")
    public LayuiPageVo myCommodity(@PathVariable("userid") String userid, int limit, int page) {
        int current = (page - 1) * limit;
        List<ShopCommodity> dataList = shopCommodityService.listByUserStatus(current, limit, userid,1);
        Integer dataNumber = shopCommodityService.countByUserStatus(userid,1);
        return new LayuiPageVo("", 0,dataNumber, dataList);
    }

}
