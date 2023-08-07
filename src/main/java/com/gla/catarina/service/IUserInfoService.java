package com.gla.catarina.service;

import com.gla.catarina.entity.UserInfo;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IUserInfoService {
    UserInfo LookUserinfo(String userid);

    List<UserInfo> queryAllUserInfo(Integer page, Integer count, Integer roleid, Integer userstatus);

    Integer queryAllUserCount(Integer roleid);

    Integer userReg(UserInfo userInfo);

    Integer UpdateUserInfo(UserInfo userInfo);

    UserInfo queryPartInfo(String userid);
}
