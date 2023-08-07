package com.gla.catarina.service.impl;

import com.gla.catarina.entity.UserRole;
import com.gla.catarina.mapper.UserRoleMapper;
import javax.annotation.Resource;

import com.gla.catarina.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class UserRoleServiceImpl implements IUserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

    /**插入角色*/
    @Override
    public Integer InsertUserRole(UserRole userRole){
        return userRoleMapper.InsertUserRole(userRole);
    }
    /**查询角色id*/
    @Override
    public Integer LookUserRoleId(String userid){
        return userRoleMapper.LookUserRoleId(userid);
    }
    /**修改用户的角色*/
    @Override
    public void UpdateUserRole(UserRole userRole){
        userRoleMapper.UpdateUserRole(userRole);
    }
}
