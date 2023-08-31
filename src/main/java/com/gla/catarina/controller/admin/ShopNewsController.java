package com.gla.catarina.controller.admin;


import com.gla.catarina.entity.ShopNews;
import com.gla.catarina.service.IShopNewsService;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.PageVo;
import com.gla.catarina.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  公告控制器
 * </p>
 *
 * @author catarina
 */
@Controller
public class ShopNewsController {
    @Resource
    private IShopNewsService shopNewsService;

    @ResponseBody
    @PostMapping("/news/insert")
    public ResultVo save(@RequestBody ShopNews shopNews, HttpSession session){
        return shopNewsService.insertNews(shopNews,session);
    }

    @ResponseBody
    @PutMapping("/news/delect/{id}")
    public ResultVo delectNews (@PathVariable("id") String id, HttpSession session) {
        return shopNewsService.delectNews(id,session);
    }

    @GetMapping("/news/detail/{id}")
    public String queryNewsById (@PathVariable("id") String id,ModelMap modelMap){
        return shopNewsService.queryNewsById(id,modelMap);
    }
    @GetMapping("/news/torelnews")
    public String torelnews (){
        return "/admin/news/relnews";
    }

    @GetMapping("/news/toupdate/{id}")
    public String toupdate (@PathVariable("id") String id, ModelMap modelMap, HttpSession session){
        return shopNewsService.toupdate(id,modelMap,session);
    }

    @ResponseBody
    @PutMapping("/news/update")
    public ResultVo updateNews (@RequestBody ShopNews shopNews){
        return shopNewsService.updateNew(shopNews);
    }

    @ResponseBody
    @GetMapping("/news/all")
    public ResultVo queryNews (){
        List<ShopNews> newslist = shopNewsService.queryNews();
        return new ResultVo(true,StatusCode.OK,"success",newslist);
    }

    @ResponseBody
    @GetMapping("/news/queryall")
    public LayuiPageVo queryAllNews(int limit, int page) {
        List<ShopNews> shopNewsList = shopNewsService.queryAllNews((page - 1) * limit, limit);
        Integer dataNumber = shopNewsService.LookNewsCount();
        return new LayuiPageVo("",0,dataNumber, shopNewsList);
    }

    @GetMapping("/news/index/number")
    @ResponseBody
    public PageVo newsNumber(){
        Integer dataNumber = shopNewsService.LookNewsCount();
        return new PageVo(StatusCode.OK,"success",dataNumber);
    }

    @GetMapping("/news/index/{page}")
    @ResponseBody
    public ResultVo newsIndex(@PathVariable("page") Integer page){
        List<ShopNews> shopNewsList = shopNewsService.queryAllNews((page - 1) * 9, 9);
        return new ResultVo(true,StatusCode.OK,"查询成功", shopNewsList);
    }

}