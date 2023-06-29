package com.gla.catarina.service;

import com.gla.catarina.entity.Comment;
import com.gla.catarina.mapper.CommentMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  评论 服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class CommentService {
    @Resource
    private CommentMapper commentMapper;

    /**插入评论*/
    public Integer insertComment(Comment comment){
        return commentMapper.insertComment(comment);
    }
    /**查询评论*/
    public List<Comment> queryComments(String commid){
        return commentMapper.queryComments(commid);
    }
    /**查询评论中用户信息*/
    public Comment queryById(String cid){
        return commentMapper.queryById(cid);
    }
    /**删除评论*/
    public Integer deleteComment(String cid){
        return commentMapper.deleteComment(cid);
    }
}
