package com.gla.catarina.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopLogin;
import com.gla.catarina.mapper.ShopLoginMapper;
import com.gla.catarina.service.IShopLoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author catarina
 */
@Service
@Transactional
public class ShopLoginServiceImpl extends ServiceImpl<ShopLoginMapper, ShopLogin> implements IShopLoginService {
    @Resource
    private ShopLoginMapper shopLoginMapper;

    /**
     * loginAdd
     */
    @Override
    public Integer saveLogin(ShopLogin shopLogin) {
        return shopLoginMapper.insert(shopLogin);
    }

    /**
     * userLogin
     */
    @Override
    public ShopLogin getUserInfo(ShopLogin shopLogin) {
        LambdaQueryWrapper<ShopLogin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != shopLogin.getUserid(), ShopLogin::getUserid, shopLogin.getUserid());
        queryWrapper.eq(null != shopLogin.getUsername(), ShopLogin::getUsername, shopLogin.getUsername());
        queryWrapper.eq(null != shopLogin.getMobilephone(), ShopLogin::getMobilephone, shopLogin.getMobilephone());
        queryWrapper.eq(null != shopLogin.getEmail(), ShopLogin::getEmail, shopLogin.getEmail());
        queryWrapper.eq(null != shopLogin.getPassword(), ShopLogin::getPassword, shopLogin.getPassword());

        return getOne(queryWrapper);
    }

    /**
     * updateLogin
     */
    @Override
    public Integer updateEntity(ShopLogin shopLogin) {
        return shopLoginMapper.updateById(shopLogin);
    }
}
