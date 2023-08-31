package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopOrders;

import java.util.List;

/**
 * @author catarina
 */
public interface IShopOrdersService extends IService<ShopOrders> {


    ShopOrders getByNumber(String ordernumber);

    void updateOrderByNumber(ShopOrders shopOrders);

    List<ShopOrders> listOrder(Integer page, Integer count, String buyuserid);

    Integer countByUserId(String buyuserid);

    List<ShopOrders> listAllSell(Integer page, Integer count, String selluserid);

    Integer countAllSell(String selluserid);
}
