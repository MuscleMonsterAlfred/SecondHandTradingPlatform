package com.gla.catarina.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopNotices;
import com.gla.catarina.mapper.ShopNoticesMapper;
import com.gla.catarina.service.IShopNoticesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 消息通知服务类
 * </p>
 *
 * @author catarina
 */
@Service
@Transactional
public class ShopNoticesServiceImpl extends ServiceImpl<ShopNoticesMapper, ShopNotices> implements IShopNoticesService {
    @Resource
    private ShopNoticesMapper shopNoticesMapper;

    /**
     * saveEntity
     */
    @Override
    public Integer saveEntity(ShopNotices shopNotices) {
        return baseMapper.insert(shopNotices);
    }

    /**
     * hasRead
     */
    @Override
    public Boolean hasRead(String id) {
        ShopNotices update = new ShopNotices();
        update.setId(id);
        update.setIsread(1);
        return updateById(update);
    }

    /**
     * listTen
     */
    @Override
    public List<ShopNotices> listTen(String userid) {
        return shopNoticesMapper.listTen(userid);
    }

    /**
     * cancel
     */
    @Override
    public Boolean cancel(String userid) {
        LambdaQueryWrapper<ShopNotices> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopNotices::getUserid, userid);

        ShopNotices update = new ShopNotices();
        update.setLatest(2);
        return update(update, queryWrapper);
    }

    /**
     * page
     */
    @Override
    public List<ShopNotices> page(Integer page, Integer count, String userid) {
        return shopNoticesMapper.page(page, count, userid);
    }

    /**
     * count
     */
    @Override
    public Integer count(String userid) {
        LambdaQueryWrapper<ShopNotices> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopNotices::getUserid, userid);
        return (int) count(queryWrapper);
    }
}
