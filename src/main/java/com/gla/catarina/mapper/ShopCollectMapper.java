package com.gla.catarina.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gla.catarina.entity.ShopCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  收藏 Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
public interface ShopCollectMapper extends BaseMapper<ShopCollect> {

    /**分页查看所有收藏内容*/
    List<ShopCollect> queryAllCollect(@Param("page") Integer page, @Param("count") Integer count, @Param("couserid") String couserid);


}
