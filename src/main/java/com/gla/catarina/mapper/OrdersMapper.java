package com.gla.catarina.mapper;

import com.gla.catarina.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  订单管理 接口
 * </p>
 *
 * @author catarina
 * @since 2021-03-06
 */
public interface OrdersMapper {
    /**插入订单*/
    void InsertOrder(Orders order);
    /**查询订单详情*/
    Orders LookOrderDetail(String ordernumber);
    /**修改订单状态*/
    void ChangeOrder(Orders orders);
    /**分页展示订单记录*/
    List<Orders> queryAllOrderecord(@Param("page") Integer page, @Param("count") Integer count, @Param("buyuserid") String buyuserid);
    /**查看订单记录总数*/
    Integer queryOrderCount(@Param("buyuserid") String buyuserid);
    /**分页展示售出记录*/
    List<Orders> queryAllSoldrecord(@Param("page") Integer page, @Param("count") Integer count, @Param("selluserid") String selluserid);
    /**查看售出记录总数*/
    Integer querySoldCount(@Param("selluserid") String selluserid);
}
