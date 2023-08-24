package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopUserPerm;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IShopUserPermService extends IService<ShopUserPerm> {
    //查询用户的权限
    List<String> LookPermsByUserid(Integer id);
}
