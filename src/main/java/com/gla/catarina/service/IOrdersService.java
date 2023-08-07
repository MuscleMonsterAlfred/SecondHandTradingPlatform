package com.gla.catarina.service;

import com.gla.catarina.entity.Orders;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IOrdersService {
    void InsertOrder(Orders order);

    Orders LookOrderDetail(String ordernumber);

    void ChangeOrder(Orders orders);

    List<Orders> queryAllOrderecord(Integer page, Integer count, String buyuserid);

    Integer queryOrderCount(String buyuserid);

    List<Orders> queryAllSoldrecord(Integer page, Integer count, String selluserid);

    Integer querySoldCount(String selluserid);
}
