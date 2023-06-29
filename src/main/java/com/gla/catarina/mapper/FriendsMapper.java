package com.gla.catarina.mapper;


import com.gla.catarina.entity.UserInfo;
import com.gla.catarina.entity.chat.Friends;

import java.util.List;

public interface FriendsMapper {
    /**添加好友*/
    void insertFriend(Friends friends);
    /**判断双方是否是好友*/
    Integer JustTwoUserIsFriend(Friends friends);
    /**查询用户的好友列表*/
    List<UserInfo> LookUserFriend(String userid);
    /**查询用户的信息*/
    UserInfo LookUserMine(String userid);
}