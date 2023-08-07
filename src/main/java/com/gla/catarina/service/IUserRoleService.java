package com.gla.catarina.service;

import com.gla.catarina.entity.UserRole;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IUserRoleService {
    Integer InsertUserRole(UserRole userRole);

    Integer LookUserRoleId(String userid);

    void UpdateUserRole(UserRole userRole);
}
