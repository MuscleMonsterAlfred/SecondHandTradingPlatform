package com.gla.catarina.controller;


import com.gla.catarina.service.ICollectService;
import com.gla.catarina.entity.Collect;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  收藏控制器
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Controller
public class CollectController {
    @Resource
    private ICollectService collectService;

    /**
     * 商品详情界面：收藏商品or取消收藏
     * 前端传入收藏操作（colloperate：1收藏，2取消收藏）,获取session中用户id信息，判断是否登录
     * (1). 收藏商品
     * 1.前端传入商品id（commid）、商品名（commname）、商品描述（commdesc）、商品用户id（cmuserid）
     *   商品用户名（username）、商品所在学校（school）
     * 2.session中获取收藏用户id（couserid）
     * 3.进行收藏操作
     * (2). 取消收藏
     * 1.前端传入商品id（commid）
     * 2.判断是否本人取消收藏
     * 3.进行取消收藏操作
     */
    @ResponseBody
    @PostMapping("/collect/operate")
    public ResultVo insertcollect(@RequestBody Collect collect, HttpSession session){
        return collectService.insertcollect(collect, session);
    }

    /**
     * 收藏列表界面取消收藏
     * 1.前端传入收藏id（id）
     * 2.判断是否本人取消收藏
     * 3.进行取消收藏操作
     */
    @ResponseBody
    @PutMapping("/collect/delete/{id}")
    public ResultVo deletecollect(@PathVariable("id") String id,HttpSession session){
        String couserid = (String) session.getAttribute("userid");
        Collect collect = new Collect().setId(id).setCouserid(couserid);
        Collect collect1 = collectService.queryCollectStatus(collect);
        /**判断是否为本人操作*/
        if (collect1.getCouserid().equals(couserid)){
            collect.setColloperate(2);
            Integer i = collectService.updateCollect(collect);
            if (i == 1){
                return new ResultVo(true, StatusCode.OK,"取消成功");
            }
            return new ResultVo(false,StatusCode.ERROR,"取消失败");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"禁止操作");
    }

    /**
     * 分页查看用户所有收藏内容
     * 前端传入页码、分页数量
     * 查询分页数据
     */
    @ResponseBody
    @GetMapping("/user/collect/queryall")
    public LayuiPageVo usercollect(int limit, int page, HttpSession session) {
        String couserid = (String) session.getAttribute("userid");
        List<Collect> collectList = collectService.queryAllCollect((page - 1) * limit, limit, couserid);
        Integer dataNumber = collectService.queryCollectCount(couserid);
        return new LayuiPageVo("",0,dataNumber,collectList);
    }
}

