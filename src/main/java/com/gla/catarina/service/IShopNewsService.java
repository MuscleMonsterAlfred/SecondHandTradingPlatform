package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopNews;
import com.gla.catarina.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IShopNewsService extends IService<ShopNews> {

    Integer delectNews(String id);


    ShopNews queryNewsById(String id);

    void addNewsRednumber(String id);

    List<ShopNews> queryNews();

    List<ShopNews> queryAllNews(@Param("page") Integer page, @Param("count") Integer count);

    Integer LookNewsCount();

    ResultVo insertNews(ShopNews shopNews, HttpSession session);

    ResultVo delectNews(String id, HttpSession session);

    String queryNewsById(String id, ModelMap modelMap);

    String toupdate(String id, ModelMap modelMap, HttpSession session);

    ResultVo updateNew(ShopNews shopNews);
}
