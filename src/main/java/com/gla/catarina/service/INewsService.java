package com.gla.catarina.service;

import com.gla.catarina.entity.News;
import com.gla.catarina.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface INewsService {
    Integer insertNews(News news);

    Integer delectNews(String id);

    Integer updateNews(News news);

    News queryNewsById(String id);

    void addNewsRednumber(String id);

    List<News> queryNews();

    List<News> queryAllNews(@Param("page") Integer page, @Param("count") Integer count);

    Integer LookNewsCount();

    ResultVo insertNews(News news, HttpSession session);

    ResultVo delectNews(String id, HttpSession session);

    String queryNewsById(String id, ModelMap modelMap);

    String toupdate(String id, ModelMap modelMap, HttpSession session);

    ResultVo updateNew(News news);
}
