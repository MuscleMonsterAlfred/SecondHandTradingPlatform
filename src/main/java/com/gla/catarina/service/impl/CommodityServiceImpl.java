package com.gla.catarina.service.impl;

import com.gla.catarina.entity.Commodity;
import com.gla.catarina.mapper.CommodityMapper;
import javax.annotation.Resource;

import com.gla.catarina.service.ICommodityService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class CommodityServiceImpl implements ICommodityService {
    @Resource
    private CommodityMapper commodityMapper;

    /**插入商品*/
    @Override
    @Async
    public Integer InsertCommodity(Commodity commodity){
        return commodityMapper.InsertCommodity(commodity);
    }
    /**查询商品详情*/
    @Override
    public Commodity LookCommodity(Commodity commodity){
        return commodityMapper.LookCommodity(commodity);
    }
    /**修改商品*/
    @Override
    public Integer ChangeCommodity(Commodity commodity){
        return commodityMapper.ChangeCommodity(commodity);
    }
    /**修改商品状态*/
    @Override
    public Integer ChangeCommstatus(String commid, Integer commstatus){
        return commodityMapper.ChangeCommstatus(commid,commstatus);
    }
    /**通过商品名分页模糊查询*/
    @Override
    public List<Commodity> queryCommodityByName(Integer page, Integer count, String commname){
        return commodityMapper.queryCommodityByName(page,count,"%"+commname+"%");
    }
    /**模糊查询商品总数*/
    @Override
    public Integer queryCommodityByNameCount(String commname){
        return commodityMapper.queryCommodityByNameCount("%"+commname+"%");
    }
    /**分页展示各类状态的商品信息*/
    @Override
    public List<Commodity> queryAllCommodity(Integer page, Integer count, String userid, Integer commstatus){
        return commodityMapper.queryAllCommodity(page,count,userid,commstatus);
    }
    /**查询商品各类状态的总数*/
    @Override
    public Integer queryCommodityCount(String userid, Integer commstatus){
        return commodityMapper.queryCommodityCount(userid,commstatus);
    }
    /**首页分类展示8条商品*/
    @Override
    public List<Commodity> queryCommodityByCategory(String category){
        return commodityMapper.queryCommodityByCategory(category);
    }
    /**产品清单分类分页展示商品*/
    @Override
    public List<Commodity> queryAllCommodityByCategory(Integer page, Integer count, String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney){
        return commodityMapper.queryAllCommodityByCategory(page,count,area,school,category,minmoney,maxmoney);
    }
    /**查询产品清单分类分页展示商品的总数*/
    @Override
    public Integer queryAllCommodityByCategoryCount(String area, String school, String category, BigDecimal minmoney, BigDecimal maxmoney){
        return commodityMapper.queryAllCommodityByCategoryCount(area,school,category,minmoney,maxmoney);
    }
}
