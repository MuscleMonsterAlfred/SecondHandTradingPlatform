package com.gla.catarina.service;

import com.gla.catarina.entity.Reply;
import com.gla.catarina.mapper.ReplyMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  评论回复 服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class ReplyService {
    @Resource
    private ReplyMapper replyMapper;

    /**插入回复*/
    public Integer insetReply(Reply reply){
        return replyMapper.insetReply(reply);
    }
    /**查询回复*/
    public List<Reply> queryReply(String cid){
        return replyMapper.queryReplys(cid);
    }
    /**查询回复中用户信息*/
    public Reply queryById(String rid){
        return replyMapper.queryById(rid);
    }
    /**删除回复*/
    public Integer deleteReply(Reply reply){
        return replyMapper.deleteReply(reply);
    }
}
