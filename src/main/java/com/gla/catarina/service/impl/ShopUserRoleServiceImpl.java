package com.gla.catarina.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopUserRole;
import com.gla.catarina.mapper.ShopUserRoleMapper;
import com.gla.catarina.service.IShopUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author catarina
 */
@Service
@Transactional
public class ShopUserRoleServiceImpl extends ServiceImpl<ShopUserRoleMapper, ShopUserRole> implements IShopUserRoleService {
    @Resource
    private ShopUserRoleMapper shopUserRoleMapper;

    /**
     * 插入角色
     */
    @Override
    public Integer saveRole(ShopUserRole shopUserRole) {
        return shopUserRoleMapper.insert(shopUserRole);
    }

    /**
     * 查询角色id
     */
    @Override
    public Integer getRoleId(String userId) {
        LambdaQueryWrapper<ShopUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopUserRole::getUserid, userId);
        List<ShopUserRole> list = list(queryWrapper);
        return CollectionUtil.isNotEmpty(list) ? list.get(0).getRoleid() : 0;
    }

    /**
     * 修改用户的角色
     */
    @Override
    public void UpdateUserRole(ShopUserRole shopUserRole) {
        shopUserRoleMapper.UpdateUserRole(shopUserRole);
    }
}
