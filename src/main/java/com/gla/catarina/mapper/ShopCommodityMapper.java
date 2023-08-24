package com.gla.catarina.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gla.catarina.entity.ShopCommodity;
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
public interface ShopCommodityMapper extends BaseMapper<ShopCommodity> {

    /**通过商品名分页模糊查询*/
    List<ShopCommodity> pageByName(@Param("current") Integer page, @Param("pageSize") Integer count, @Param("cName") String cName);

    /**分页展示各类状态的商品信息*/
    List<ShopCommodity> listByUserStatus(@Param("userid") String userid,@Param("page") Integer page, @Param("count") Integer count,  @Param("commstatus") Integer commstatus);
    /**首页分类展示8条商品*/
    List<ShopCommodity> listByCategory(@Param("category") String category);
    /**产品清单分类分页展示商品*/

}