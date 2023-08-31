package com.gla.catarina.controller;


import com.gla.catarina.entity.ShopCollect;
import com.gla.catarina.service.IShopCollectService;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 收藏控制器
 * </p>
 *
 * @author catarina
 */
@Controller
public class ShopCollectController {
    @Resource
    private IShopCollectService collectService;

    /**
     * 商品详情界面：收藏商品or取消收藏
     * 前端传入收藏操作（colloperate：1收藏，2取消收藏）,获取session中用户id信息，判断是否登录
     * (1). 收藏商品
     * 1.前端传入商品id（commid）、商品名（commname）、商品描述（commdesc）、商品用户id（cmuserid）
     * 商品用户名（username）、商品所在学校（school）
     * 2.session中获取收藏用户id（couserid）
     * 3.进行收藏操作
     * (2). 取消收藏
     * 1.前端传入商品id（commid）
     * 2.判断是否本人取消收藏
     * 3.进行取消收藏操作
     */
    @ResponseBody
    @PostMapping("/collect/operate")
    public ResultVo insert(@RequestBody ShopCollect collect, HttpSession session) {
        String userId = (String) session.getAttribute("userid");
        collect.setCouserid(userId);
        return collectService.insert(collect);
    }

    /**
     * 收藏列表界面取消收藏
     * 1.前端传入收藏id（id）
     * 2.判断是否本人取消收藏
     * 3.进行取消收藏操作
     */
    @ResponseBody
    @PutMapping("/collect/delete/{id}")
    public ResultVo del(@PathVariable("id") String id, HttpSession session) {
        String userId = (String) session.getAttribute("userid");
        ShopCollect collect = collectService.queryById(id, userId);

        if (null != collect && collectService.removeById(id)) {
            return new ResultVo(true, StatusCode.OK, "Success");
        }
        return new ResultVo(false, StatusCode.ACCESSERROR, "Fail");
    }

    /**
     * 分页查看用户所有收藏内容
     * 前端传入页码、分页数量
     * 查询分页数据
     */
    @ResponseBody
    @GetMapping("/user/collect/queryAll")
    public LayuiPageVo page(int limit, int page, HttpSession session) {
        String userId = (String) session.getAttribute("userid");
        return collectService.page((page - 1) * limit, limit, userId);
    }
}

