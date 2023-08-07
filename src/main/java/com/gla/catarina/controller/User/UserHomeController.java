package com.gla.catarina.controller.User;

import com.gla.catarina.service.ICommodityService;
import com.gla.catarina.service.IUserInfoService;
import com.gla.catarina.entity.Commodity;
import com.gla.catarina.entity.UserInfo;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: catarina
 * @Description: 个人主页
 * @Date: 2023/2/23 18:07
 */
@Controller
public class UserHomeController {
    @Resource
    private IUserInfoService IUserInfoService;
    @Resource
    private ICommodityService ICommodityService;

    /**
     * 个人简介
     * 前端传入用户id（userid）
     */
    @ResponseBody
    @GetMapping("/user/userinfo/{userid}")
    public ResultVo userinfo(@PathVariable("userid") String userid) {
        UserInfo userInfo = IUserInfoService.LookUserinfo(userid);
        if (!StringUtils.isEmpty(userInfo)){
            return new ResultVo(true, StatusCode.OK, "查询成功",userInfo);
        }
        return new ResultVo(false, StatusCode.ERROR, "查询失败");
    }

    /**
     * 分页展示个人已审核的商品信息（状态码：1）
     *前端传入用户id（userid）、当前页码（nowPaging）、
     */
    @ResponseBody
    @GetMapping("/user/usercommodity/{userid}")
    public LayuiPageVo userHomeCommodity(@PathVariable("userid") String userid,int limit, int page) {
        List<Commodity> commodityList = ICommodityService.queryAllCommodity((page - 1) * limit, limit, userid,1);
        Integer dataNumber = ICommodityService.queryCommodityCount(userid,1);
        return new LayuiPageVo("", 0,dataNumber,commodityList);
    }

}
