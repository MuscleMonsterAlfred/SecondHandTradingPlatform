package com.gla.catarina.service;

import com.gla.catarina.mapper.UserPermMapper;
import javax.annotation.Resource;
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
public class UserPermService {
    @Resource
    private UserPermMapper userPermMapper;

    //查询用户的权限
    public List<String> LookPermsByUserid(Integer id){
        return userPermMapper.LookPermsByUserid(id);
    }
}
