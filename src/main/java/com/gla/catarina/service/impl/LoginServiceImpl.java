package com.gla.catarina.service.impl;

import com.gla.catarina.entity.Login;
import com.gla.catarina.mapper.LoginMapper;
import javax.annotation.Resource;

import com.gla.catarina.service.ILoginService;
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
public class LoginServiceImpl implements ILoginService {
    @Resource
    private LoginMapper loginMapper;

    /**注册*/
    @Override
    public Integer loginAdd(Login login){
        return loginMapper.loginAdd(login);
    }
    /**登录及判断用户是否存在*/
    @Override
    public Login userLogin(Login login){
        return loginMapper.userLogin(login);
    }
    /**修改登录信息*/
    @Override
    public Integer updateLogin(Login login){
        return loginMapper.updateLogin(login);
    }
}
