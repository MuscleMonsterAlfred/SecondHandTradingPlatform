package com.gla.catarina.service.impl;

import com.gla.catarina.entity.Notices;
import com.gla.catarina.mapper.NoticesMapper;
import com.gla.catarina.service.INoticesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  消息通知服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-25
 */
@Service
@Transactional
public class NoticesServiceImpl implements INoticesService {
    @Resource
    private NoticesMapper noticesMapper;

    /**发出通知消息*/
    @Override
    public Integer insertNotices(Notices notices){
        return noticesMapper.insertNotices(notices);
    }
    /**用户已读通知消息*/
    @Override
    public Integer updateNoticesById(String id){
        return noticesMapper.updateNoticesById(id);
    }
    /**查询前10条通知*/
    @Override
    public List<Notices> queryNotices(String userid){
        return noticesMapper.queryNotices(userid);
    }
    /**取消新通知标志*/
    @Override
    public Integer CancelLatest(String userid){
        return noticesMapper.CancelLatest(userid);
    }
    /**分页查询用户所有通知消息*/
    @Override
    public List<Notices> queryAllNotices(Integer page, Integer count, String userid){
        return noticesMapper.queryAllNotices(page,count,userid);
    }
    /**查询用户所有通知消息的数量*/
    @Override
    public Integer queryNoticesCount(String userid){
        return noticesMapper.queryNoticesCount(userid);
    }
}
