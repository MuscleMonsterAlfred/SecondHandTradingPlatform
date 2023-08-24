package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopUserRole;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IShopUserRoleService extends IService<ShopUserRole> {
    Integer saveRole(ShopUserRole shopUserRole);

    Integer getRoleId(String userId);

    void UpdateUserRole(ShopUserRole shopUserRole);
}
