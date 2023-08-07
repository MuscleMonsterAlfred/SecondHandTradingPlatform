package com.gla.catarina.service;

import com.gla.catarina.entity.Reply;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IReplyService {
    Integer insetReply(Reply reply);

    List<Reply> queryReply(String cid);

    Reply queryById(String rid);

    Integer deleteReply(Reply reply);
}
