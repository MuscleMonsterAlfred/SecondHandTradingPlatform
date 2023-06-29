package com.gla.catarina.mapper;

import com.gla.catarina.entity.UserRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
public interface UserRoleMapper {
    /**插入角色*/
    Integer InsertUserRole(UserRole userRole);
    /**查询角色id*/
    Integer LookUserRoleId(String userid);
    /**修改用户的角色*/
    void UpdateUserRole(UserRole userRole);
}
