package com.gla.catarina.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopReply;
import com.gla.catarina.mapper.ShopReplyMapper;
import com.gla.catarina.service.IShopReplyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 评论回复 服务类
 * </p>
 *
 * @author catarina
 */
@Service
@Transactional
public class ShopReplyServiceImpl extends ServiceImpl<ShopReplyMapper, ShopReply> implements IShopReplyService {
    @Resource
    private ShopReplyMapper shopReplyMapper;


    @Override
    public List<ShopReply> listByCId(String cid) {
        LambdaQueryWrapper<ShopReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopReply::getCid, cid);
        return list(queryWrapper);
    }

    @Override
    public Boolean delByCId(ShopReply cid) {
        LambdaQueryWrapper<ShopReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopReply::getCid, cid);
        return remove(queryWrapper);
    }
}
