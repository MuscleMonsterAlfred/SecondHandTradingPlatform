package com.gla.catarina.mapper;

import com.gla.catarina.entity.Login;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
public interface LoginMapper {
    /**注册*/
    Integer loginAdd(Login login);
    /**登录及判断用户是否存在*/
    Login userLogin(Login login);
    /**修改登录信息*/
    Integer updateLogin(Login login);
}