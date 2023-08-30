package com.gla.catarina.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopCommodity;
import com.gla.catarina.mapper.ShopCommodityMapper;
import com.gla.catarina.service.IShopCommodityService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class ShopCommodityServiceImpl extends ServiceImpl<ShopCommodityMapper, ShopCommodity> implements IShopCommodityService {
    @Resource
    private ShopCommodityMapper shopCommodityMapper;


    /**
     * 修改商品状态
     */
    @Override
    public Integer updateStatus(String commid, Integer commstatus) {
        ShopCommodity entity = new ShopCommodity();
        entity.setCommid(commid);
        entity.setCommstatus(commstatus);
        return shopCommodityMapper.updateById(entity);
    }

    /**
     * 通过商品名分页模糊查询
     */
    @Override
    public List<ShopCommodity> pageByName(Integer pageNum, String cName) {
        return shopCommodityMapper.pageByName((pageNum - 1) * 20, 20, "%" + cName + "%");
    }

    /**
     * 模糊查询商品总数
     */
    @Override
    public Integer countByName(String cName) {
        LambdaQueryWrapper<ShopCommodity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ShopCommodity::getCommname, cName);
        queryWrapper.eq(ShopCommodity::getCommstatus, 1);
        return shopCommodityMapper.selectCount(queryWrapper).intValue();
    }

    /**
     * 分页展示各类状态的商品信息
     */
    @Override
    public List<ShopCommodity> listByUserStatus(Integer page, Integer count, String userid, Integer commstatus) {
        return shopCommodityMapper.listByUserStatus(userid, page, count, commstatus);
    }

    /**
     * 查询商品各类状态的总数
     */
    @Override
    public Integer countByUserStatus(String userid, Integer commStatus) {
        LambdaQueryWrapper<ShopCommodity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!StringUtils.isNotBlank(userid), ShopCommodity::getUserid, userid);
        queryWrapper.eq(null != commStatus, ShopCommodity::getCommstatus, commStatus);
        return (int) count(queryWrapper);
    }

    /**
     * 首页分类展示8条商品
     */
    @Override
    public List<ShopCommodity> listByCategory(String category) {
        return shopCommodityMapper.listByCategory(category);
    }

    /**
     * 产品清单分类分页展示商品
     */
    @Override
    public List<ShopCommodity> listAll(Integer page, Integer count, String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney) {
        LambdaQueryWrapper<ShopCommodity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"All".equals(category), ShopCommodity::getCategory, category);
        queryWrapper.eq(!"All".equals(school), ShopCommodity::getSchool, school);
        queryWrapper.eq(ShopCommodity::getCommstatus, 1);
        queryWrapper.between(minmoney != null && maxmoney != null, ShopCommodity::getCommstatus, minmoney, maxmoney);
        return list(queryWrapper);
    }

    /**
     * 查询产品清单分类分页展示商品的总数
     */
    @Override
    public Integer countAllByCategoryCount(String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney) {
        LambdaQueryWrapper<ShopCommodity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(!"All".equals(category), ShopCommodity::getCategory, category);
        queryWrapper.notIn("Vicinity".equals(area), ShopCommodity::getSchool, school);
        queryWrapper.eq(!"All".equals(school), ShopCommodity::getSchool, school);
        queryWrapper.eq(ShopCommodity::getCommstatus, 1);
        queryWrapper.between(minmoney != null && maxmoney != null, ShopCommodity::getThinkmoney, minmoney, maxmoney);
        return shopCommodityMapper.selectCount(queryWrapper).intValue();
    }

    @Override
    public void incrReadNum(String commid) {
        ShopCommodity commodity = getById(commid);
        ShopCommodity update = new ShopCommodity();
        update.setCommid(commid);
        update.setRednumber(commodity.getRednumber() + 1);
        updateById(update);
    }
}
