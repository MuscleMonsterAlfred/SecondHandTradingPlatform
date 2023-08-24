package com.gla.catarina.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gla.catarina.entity.ShopUserRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
public interface ShopUserRoleMapper extends BaseMapper<ShopUserRole> {

    /**修改用户的角色*/
    void UpdateUserRole(ShopUserRole shopUserRole);
}
