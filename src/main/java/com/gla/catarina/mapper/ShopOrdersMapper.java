package com.gla.catarina.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gla.catarina.entity.ShopOrders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  订单管理 接口
 * </p>
 *
 * @author catarina
 */
public interface ShopOrdersMapper extends BaseMapper<ShopOrders> {

    /**listOrder*/
    List<ShopOrders> listOrder(@Param("page") Integer page, @Param("count") Integer count, @Param("buyuserid") String buyuserid);
    /**countByUserId*/
    Integer countByUserId(@Param("buyuserid") String buyuserid);
    /**listAllSell*/
    List<ShopOrders> listAllSell(@Param("page") Integer page, @Param("count") Integer count, @Param("selluserid") String selluserid);
    /**countAllSell*/
    Integer countAllSell(@Param("selluserid") String sellUserId);
}
