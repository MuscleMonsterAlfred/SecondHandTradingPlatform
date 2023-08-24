package com.gla.catarina.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gla.catarina.entity.ShopNews;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
public interface ShopNewsMapper extends BaseMapper<ShopNews> {
    /**发布公告*/
    /**删除公告*/
    Integer delectNews(String id);

    /**查看公告详情*/
    ShopNews queryNewsById(String id);
    /**浏览量*/
    void addNewsRednumber(String id);
    /**查询前三条公告*/
    List<ShopNews> queryNews();
    /**分页展示公告信息*/
    List<ShopNews> queryAllNews(@Param("page") Integer page, @Param("count") Integer count);
    /**查找所有公告的总数*/
    Integer LookNewsCount();
}
