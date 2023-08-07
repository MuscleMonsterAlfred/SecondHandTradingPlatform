package com.gla.catarina.service;

import com.gla.catarina.entity.Commodity;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface ICommodityService {
    @Async
    Integer InsertCommodity(Commodity commodity);

    Commodity LookCommodity(Commodity commodity);

    Integer ChangeCommodity(Commodity commodity);

    Integer ChangeCommstatus(String commid, Integer commstatus);

    List<Commodity> queryCommodityByName(Integer page, Integer count, String commname);

    Integer queryCommodityByNameCount(String commname);

    List<Commodity> queryAllCommodity(Integer page, Integer count, String userid, Integer commstatus);

    Integer queryCommodityCount(String userid, Integer commstatus);

    List<Commodity> queryCommodityByCategory(String category);

    List<Commodity> queryAllCommodityByCategory(Integer page, Integer count, String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney);

    Integer queryAllCommodityByCategoryCount(String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney);
}
