package com.gla.catarina.service;

import com.gla.catarina.mapper.OrdersMapper;
import com.gla.catarina.entity.Orders;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  订单管理服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-06
 */
@Service
@Transactional
public class OrdersService {
    @Resource
    private OrdersMapper orderMapper;

    public void InsertOrder(Orders order){
        orderMapper.InsertOrder(order);
    }
    public Orders LookOrderDetail(String ordernumber){
        return orderMapper.LookOrderDetail(ordernumber);
    }
    public void ChangeOrder(Orders orders){
        orderMapper.ChangeOrder(orders);
    }

    /**分页展示个人的订单记录*/
    public List<Orders> queryAllOrderecord(Integer page, Integer count, String buyuserid){
        return orderMapper.queryAllOrderecord(page,count,buyuserid);
    }
    /**查看订单记录总数*/
    public Integer queryOrderCount(String buyuserid){
        return orderMapper.queryOrderCount(buyuserid);
    }

    /**分页展示个人的售出记录*/
    public List<Orders> queryAllSoldrecord(Integer page, Integer count, String selluserid){
        return orderMapper.queryAllSoldrecord(page,count,selluserid);
    }
    /**查看售出记录总数*/
    public Integer querySoldCount(String selluserid){
        return orderMapper.querySoldCount(selluserid);
    }
}