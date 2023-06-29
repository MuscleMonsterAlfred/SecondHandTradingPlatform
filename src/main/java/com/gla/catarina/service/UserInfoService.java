package com.gla.catarina.service;

import com.gla.catarina.entity.UserInfo;
import com.gla.catarina.mapper.UserInfoMapper;
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
public class UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    /**查询用户信息*/
    public UserInfo LookUserinfo(String userid) {
        return userInfoMapper.LookUserinfo(userid);
    }
    /**分页查询不同角色用户信息*/
    public List<UserInfo> queryAllUserInfo(Integer page,Integer count,Integer roleid,Integer userstatus){
        return userInfoMapper.queryAllUserInfo(page,count,roleid,userstatus);
    }
    /**查看不同角色用户总数*/
    public Integer queryAllUserCount(Integer roleid){
        return userInfoMapper.queryAllUserCount(roleid);
    }
    /**添加用户信息*/
    public Integer userReg(UserInfo userInfo){
        return userInfoMapper.userReg(userInfo);
    }
    /**修改用户信息*/
    public Integer UpdateUserInfo(UserInfo userInfo){
        return userInfoMapper.UpdateUserInfo(userInfo);
    }
    /**查询用户的昵称和头像**/
    public UserInfo queryPartInfo(String userid){
        return userInfoMapper.queryPartInfo(userid);
    }
}
