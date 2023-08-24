package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopReply;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IShopReplyService extends IService<ShopReply> {



    List<ShopReply> listByCId(String cid);

    Boolean delByCId(ShopReply setCid);
}
