package com.gla.catarina.service;

import com.gla.catarina.entity.UserInfo;
import com.gla.catarina.entity.chat.Friends;
import com.gla.catarina.mapper.FriendsMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FriendsService {
    @Resource
    FriendsMapper friendsMapper;

    /**添加好友*/
    public void insertFriend(Friends friends){
        friendsMapper.insertFriend(friends);
    }

    /**判断双方是否是好友*/
    public Integer JustTwoUserIsFriend(Friends friends){
        return friendsMapper.JustTwoUserIsFriend(friends);
    }

    /**查询用户的好友列表*/
    public List<UserInfo> LookUserFriend(String userid){
        return friendsMapper.LookUserFriend(userid);
    }

    /**查询用户的信息*/
    public UserInfo LookUserMine(String userid){
        return friendsMapper.LookUserMine(userid);
    }
}