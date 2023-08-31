package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopCommodity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author catarina
 */
public interface IShopCommodityService extends IService<ShopCommodity> {
    Integer updateStatus(String commid, Integer commstatus);

    List<ShopCommodity> pageByName(Integer pageNum,  String cName);

    Integer countByName(String cName);

    List<ShopCommodity> listByUserStatus(Integer page, Integer count, String userid, Integer commstatus);

    Integer countByUserStatus(String userid, Integer commStatus);

    List<ShopCommodity> listByCategory(String category);

    List<ShopCommodity> listAll(Integer page, Integer count, String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney);

    Integer countAllByCategoryCount(String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney);

    /**
     * 浏览数量增加
     * @param commid
     */
    void incrReadNum(String commid);
}
