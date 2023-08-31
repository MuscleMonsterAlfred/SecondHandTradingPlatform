package com.gla.catarina.controller;


import com.gla.catarina.entity.ShopNotices;
import com.gla.catarina.service.IShopNoticesService;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static com.gla.catarina.util.ServletUtils.getUserId;

/**
 * <p>
 * 消息通知控制器
 * </p>
 *
 * @author catarina
 */
@Controller
public class ShopNoticesController {
    @Resource
    private IShopNoticesService shopNoticesService;

    /**
     * hasRead
     */
    @ResponseBody
    @PutMapping("/notices/look/{id}")
    public ResultVo hasRead(@PathVariable("id") String id) {
        Boolean aBoolean = shopNoticesService.hasRead(id);
        if (aBoolean) {
            return ResultVo.ok();
        }
        return ResultVo.error();
    }

    /**
     * list
     **/
    @ResponseBody
    @GetMapping("/notices/queryNotices")
    public ResultVo list() {
        List<ShopNotices> shopNoticesList = shopNoticesService.listTen(getUserId());
        return ResultVo.ok(shopNoticesList);
    }

    /**
     * cancel
     */
    @ResponseBody
    @GetMapping("/notices/cancelLatest")
    public ResultVo cancel() {
        if (shopNoticesService.cancel(getUserId())) {
            return ResultVo.ok();
        }
        return ResultVo.error();
    }

    /**
     * page
     */
    @ResponseBody
    @GetMapping("/notices/queryall")
    public LayuiPageVo page(int limit, int page) {
        int current = (page - 1) * limit;
        String userId = getUserId();
        List<ShopNotices> shopNoticesList = shopNoticesService.page(current, limit, userId);
        Integer dataNumber = shopNoticesService.count(userId);
        return new LayuiPageVo("", 0, dataNumber, shopNoticesList);
    }

}

