package com.gla.catarina.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopUserInfo;
import com.gla.catarina.mapper.ShopUserInfoMapper;
import com.gla.catarina.service.IShopUserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author catarina
 */
@Service
@Transactional
public class ShopUserInfoServiceImpl extends ServiceImpl<ShopUserInfoMapper, ShopUserInfo> implements IShopUserInfoService {
    @Resource
    private ShopUserInfoMapper shopUserInfoMapper;

    /**getByUserId*/
    @Override
    public ShopUserInfo getByUserId(String userid) {
        return shopUserInfoMapper.selectById(userid);
    }
    /**queryAllUserInfo*/
    @Override
    public List<ShopUserInfo> queryAllUserInfo(Integer page, Integer count, Integer roleid, Integer userstatus){
        return shopUserInfoMapper.queryAllUserInfo(page,count,roleid,userstatus);
    }
    /**queryAllUserCount*/
    @Override
    public Integer queryAllUserCount(Integer roleid){
        return shopUserInfoMapper.queryAllUserCount(roleid);
    }
    /**saveUser*/
    @Override
    public Integer saveUser(ShopUserInfo shopUserInfo){
        return shopUserInfoMapper.insert(shopUserInfo);
    }
    /**updateEntity*/
    @Override
    public Integer updateEntity(ShopUserInfo shopUserInfo){
        return shopUserInfoMapper.updateById(shopUserInfo);
    }
}
