package com.gla.catarina.service.impl;

import com.gla.catarina.entity.Commimages;
import com.gla.catarina.mapper.CommimagesMapper;
import javax.annotation.Resource;

import com.gla.catarina.service.ICommimagesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class CommimagesServiceImpl implements ICommimagesService {
    @Resource
    private CommimagesMapper commimagesMapper;

    /**插入商品的Other图*/
    @Override
    public void InsertGoodImages(List<Commimages> list){
        commimagesMapper.InsertGoodImages(list);
    }
    /**查询某个商品得Other图*/
    @Override
    public List<String> LookGoodImages(String commid){
        return commimagesMapper.LookGoodImages(commid);
    }
    /**删除某个商品得Other图*/
    @Override
    public void DelGoodImages(String commid){
        commimagesMapper.DelGoodImages(commid);
    }
}
