package com.gla.catarina.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopOrders;
import com.gla.catarina.mapper.ShopOrdersMapper;
import com.gla.catarina.service.IShopOrdersService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 订单管理服务类
 * </p>
 *
 * @author catarina
 */
@Service
@Transactional
public class ShopOrdersServiceImpl extends ServiceImpl<ShopOrdersMapper, ShopOrders> implements IShopOrdersService {
    @Resource
    private ShopOrdersMapper orderMapper;


    @Override
    public ShopOrders getByNumber(String orderNumber) {
        LambdaQueryWrapper<ShopOrders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopOrders::getOrdernumber, orderNumber);
        return getOne(queryWrapper);
    }

    @Override
    public void updateOrderByNumber(ShopOrders shopOrders) {
        ShopOrders byNumber = getByNumber(shopOrders.getOrdernumber());
        shopOrders.setId(byNumber.getId());
        updateById(shopOrders);
    }

    /**
     * listOrder
     */
    @Override
    public List<ShopOrders> listOrder(Integer page, Integer count, String buyuserid) {
        return orderMapper.listOrder(page, count, buyuserid);
    }

    /**
     * countByUserId
     */
    @Override
    public Integer countByUserId(String buyuserid) {
        return orderMapper.countByUserId(buyuserid);
    }

    /**
     * listAllSell
     */
    @Override
    public List<ShopOrders> listAllSell(Integer page, Integer count, String selluserid) {
        return orderMapper.listAllSell(page, count, selluserid);
    }

    /**
     * countAllSell
     */
    @Override
    public Integer countAllSell(String sellUserId) {
        return orderMapper.countAllSell(sellUserId);
    }
}
