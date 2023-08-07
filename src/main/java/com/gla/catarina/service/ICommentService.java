package com.gla.catarina.service;

import com.gla.catarina.entity.Comment;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface ICommentService {
    Integer insertComment(Comment comment);

    List<Comment> queryComments(String commid);

    Comment queryById(String cid);

    Integer deleteComment(String cid);
}
