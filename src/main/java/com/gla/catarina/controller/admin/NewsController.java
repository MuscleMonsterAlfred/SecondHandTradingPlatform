package com.gla.catarina.controller.admin;


import com.gla.catarina.service.INewsService;
import com.gla.catarina.entity.News;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.PageVo;
import com.gla.catarina.vo.ResultVo;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  公告控制器
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Controller
public class NewsController {
    @Resource
    private INewsService INewsService;

    @ResponseBody
    @PostMapping("/news/insert")
    public ResultVo insertNews(@RequestBody News news, HttpSession session){
        return INewsService.insertNews(news,session);
    }

    @ResponseBody
    @PutMapping("/news/delect/{id}")
    public ResultVo delectNews (@PathVariable("id") String id, HttpSession session) {
        return INewsService.delectNews(id,session);
    }

    @GetMapping("/news/detail/{id}")
    public String queryNewsById (@PathVariable("id") String id,ModelMap modelMap){
        return INewsService.queryNewsById(id,modelMap);
    }
    @GetMapping("/news/torelnews")
    public String torelnews (){
        return "/admin/news/relnews";
    }

    @GetMapping("/news/toupdate/{id}")
    public String toupdate (@PathVariable("id") String id, ModelMap modelMap, HttpSession session){
        return INewsService.toupdate(id,modelMap,session);
    }

    @ResponseBody
    @PutMapping("/news/update")
    public ResultVo updateNews (@RequestBody News news){
        return INewsService.updateNew(news);
    }

    @ResponseBody
    @GetMapping("/news/all")
    public ResultVo queryNews (){
        List<News> newslist = INewsService.queryNews();
        return new ResultVo(true,StatusCode.OK,"success",newslist);
    }

    @ResponseBody
    @GetMapping("/news/queryall")
    public LayuiPageVo queryAllNews(int limit, int page) {
        List<News> newsList = INewsService.queryAllNews((page - 1) * limit, limit);
        Integer dataNumber = INewsService.LookNewsCount();
        return new LayuiPageVo("",0,dataNumber,newsList);
    }

    @GetMapping("/news/index/number")
    @ResponseBody
    public PageVo newsNumber(){
        Integer dataNumber = INewsService.LookNewsCount();
        return new PageVo(StatusCode.OK,"success",dataNumber);
    }

    @GetMapping("/news/index/{page}")
    @ResponseBody
    public ResultVo newsIndex(@PathVariable("page") Integer page){
        List<News> newsList = INewsService.queryAllNews((page - 1) * 9, 9);
        return new ResultVo(true,StatusCode.OK,"查询成功",newsList);
    }

}