package com.gla.catarina.mapper;

import com.gla.catarina.entity.Commimages;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
public interface CommimagesMapper {
    /**插入商品的Other图*/
    void InsertGoodImages(List<Commimages> list);
    /**查询某个商品得Other图*/
    List<String> LookGoodImages(String commid);
    /**删除某个商品得Other图*/
    void DelGoodImages(String commid);
}
