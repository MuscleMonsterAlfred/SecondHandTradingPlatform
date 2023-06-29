package com.gla.catarina.service;

import com.gla.catarina.entity.UserInfo;
import com.gla.catarina.entity.chat.ChatMsg;
import com.gla.catarina.mapper.ChatmsgMapper;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatmsgService {
    @Resource
    ChatmsgMapper chatmsgMapper;

    /**插入发送的消息记录*/
    @Async
    public void insertChatmsg(ChatMsg chatmsg){
        chatmsgMapper.insertChatmsg(chatmsg);
    }

    /**查询聊天记录*/
    public List<UserInfo> LookChatMsg(ChatMsg chatMsg){
        return chatmsgMapper.LookChatMsg(chatMsg);
    }
}
