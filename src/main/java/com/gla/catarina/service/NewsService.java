package com.gla.catarina.service;

import com.gla.catarina.entity.News;
import com.gla.catarina.mapper.NewsMapper;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class NewsService {
    @Resource
    private NewsMapper newsMapper;

    /**发布公告*/
    public Integer insertNews(News news){
        return newsMapper.insertNews(news);
    }

    /**删除公告*/
    public Integer delectNews(String id){
        return newsMapper.delectNews(id);
    }

    /**修改公告*/
    public Integer updateNews(News news){
        return newsMapper.updateNews(news);
    }

    /**查看公告详情*/
    public News queryNewsById(String id){
        return newsMapper.queryNewsById(id);
    }

    /**浏览量*/
    public void addNewsRednumber(String id){
        newsMapper.addNewsRednumber(id);
    }

    /**查询前三条公告*/
    public List<News> queryNews(){
        return newsMapper.queryNews();
    }

    /**分页展示公告信息*/
    public List<News> queryAllNews(@Param("page") Integer page, @Param("count") Integer count){
        return newsMapper.queryAllNews(page,count);
    }

    /**查找所有公告的总数*/
    public Integer LookNewsCount(){
        return newsMapper.LookNewsCount();
    }

    public ResultVo insertNews(News news, HttpSession session){
        String username=(String) session.getAttribute("username");
        news.setId(KeyUtil.genUniqueKey()).setUsername(username);
        Integer i = this.insertNews(news);
        if (i == 1){
            return new ResultVo(true, StatusCode.OK,"公告发布成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"公告发布失败，请重新发布");
    }

    public ResultVo delectNews (String id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        News news = this.queryNewsById(id);
        if (StringUtils.isEmpty(news)){
            return new ResultVo(false,StatusCode.FINDERROR,"找不到要删除的公告");
        }else {
            /**判断是否是本人或超级管理员*/
            if (news.getUsername().equals(username) || username.equals("admin")){
                Integer i = this.delectNews(id);
                if (i == 1){
                    return new ResultVo(true,StatusCode.OK,"删除成功");
                }
                return new ResultVo(false,StatusCode.ERROR,"删除失败");
            }else {
                return new ResultVo(false,StatusCode.ACCESSERROR,"权限不足，无法删除");
            }
        }
    }

    public String queryNewsById (String id, ModelMap modelMap){
        this.addNewsRednumber(id);
        News news = this.queryNewsById(id);
        if (StringUtils.isEmpty(news)){
            return "/error/404";
        }
        modelMap.put("news",news);
        return "/common/newsdetail";
    }

    public String toupdate (String id, ModelMap modelMap, HttpSession session){
        String username=(String) session.getAttribute("username");
        News news = this.queryNewsById(id);
        /**如果是本人则可以跳转修改*/
        if (news.getUsername().equals(username)){
            modelMap.put("qx",1);
            modelMap.put("news",news);
            return "/admin/news/updatenews";
        }
        modelMap.put("news",news);
        modelMap.put("qx",0);
        return "/admin/news/updatenews";
    }

    public ResultVo updateNew (News news){
        Integer i = this.updateNews(news);
        if (i == 1){
            return new ResultVo(true,StatusCode.OK,"修改成功");
        }
        return new ResultVo(false,StatusCode.ERROR,"修改失败");
    }
}
