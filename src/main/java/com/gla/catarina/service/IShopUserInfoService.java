package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopUserInfo;

import java.util.List;

/**
 * @author catarina
 */
public interface IShopUserInfoService extends IService<ShopUserInfo> {
    ShopUserInfo getByUserId(String userid);

    List<ShopUserInfo> queryAllUserInfo(Integer page, Integer count, Integer roleid, Integer userstatus);

    Integer queryAllUserCount(Integer roleid);

    Integer saveUser(ShopUserInfo shopUserInfo);

    Integer updateEntity(ShopUserInfo shopUserInfo);

}
