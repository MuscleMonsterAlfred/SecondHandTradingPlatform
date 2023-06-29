package com.gla.catarina.service;

import com.gla.catarina.entity.Collect;
import com.gla.catarina.mapper.CollectMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  收藏 服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class CollectService {
    @Resource
    private CollectMapper collectMapper;

    /**添加收藏*/
    public Integer insertCollect(Collect collect){
        return collectMapper.insertCollect(collect);
    }
    /**分页查看所有收藏内容*/
    public List<Collect> queryAllCollect(Integer page,Integer count,String couserid){
        return collectMapper.queryAllCollect(page,count,couserid);
    }
    /**修改收藏状态*/
    public Integer updateCollect(Collect collect){
        return collectMapper.updateCollect(collect);
    }
    /**查询商品是否被用户收藏*/
    public Collect queryCollectStatus(Collect collect){
        return collectMapper.queryCollectStatus(collect);
    }
    /**查询我的收藏的总数*/
    public Integer queryCollectCount(String couserid){
        return collectMapper.queryCollectCount(couserid);
    }
}
