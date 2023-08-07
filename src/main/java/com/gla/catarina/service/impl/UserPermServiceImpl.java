package com.gla.catarina.service.impl;

import com.gla.catarina.mapper.UserPermMapper;
import javax.annotation.Resource;

import com.gla.catarina.service.IUserPermService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
public class UserPermServiceImpl implements IUserPermService {
    @Resource
    private UserPermMapper userPermMapper;

    //查询用户的权限
    @Override
    public List<String> LookPermsByUserid(Integer id){
        return userPermMapper.LookPermsByUserid(id);
    }
}
