package com.gla.catarina.controller.admin;


import com.gla.catarina.service.NewsService;
import com.gla.catarina.entity.News;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.PageVo;
import com.gla.catarina.vo.ResultVo;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
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
    private NewsService newsService;

    @ResponseBody
    @PostMapping("/news/insert")
    public ResultVo insertNews(@RequestBody News news, HttpSession session){
        return newsService.insertNews(news,session);
    }

    @ResponseBody
    @PutMapping("/news/delect/{id}")
    public ResultVo delectNews (@PathVariable("id") String id, HttpSession session) {
        return newsService.delectNews(id,session);
    }

    @GetMapping("/news/detail/{id}")
    public String queryNewsById (@PathVariable("id") String id,ModelMap modelMap){
        return queryNewsById(id,modelMap);
    }
    @GetMapping("/news/torelnews")
    public String torelnews (){
        return "/admin/news/relnews";
    }

    @GetMapping("/news/toupdate/{id}")
    public String toupdate (@PathVariable("id") String id, ModelMap modelMap, HttpSession session){
        return newsService.toupdate(id,modelMap,session);
    }

    @ResponseBody
    @PutMapping("/news/update")
    public ResultVo updateNews (@RequestBody News news){
        return newsService.updateNew(news);
    }

    @ResponseBody
    @GetMapping("/news/all")
    public ResultVo queryNews (){
        List<News> newslist = newsService.queryNews();
        return new ResultVo(true,StatusCode.OK,"查询成功",newslist);
    }

    @ResponseBody
    @GetMapping("/news/queryall")
    public LayuiPageVo queryAllNews(int limit, int page) {
        List<News> newsList = newsService.queryAllNews((page - 1) * limit, limit);
        Integer dataNumber = newsService.LookNewsCount();
        return new LayuiPageVo("",0,dataNumber,newsList);
    }

    @GetMapping("/news/index/number")
    @ResponseBody
    public PageVo newsNumber(){
        Integer dataNumber = newsService.LookNewsCount();
        return new PageVo(StatusCode.OK,"查询成功",dataNumber);
    }

    @GetMapping("/news/index/{page}")
    @ResponseBody
    public ResultVo newsIndex(@PathVariable("page") Integer page){
        List<News> newsList = newsService.queryAllNews((page - 1) * 9, 9);
        return new ResultVo(true,StatusCode.OK,"查询成功",newsList);
    }

}