package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopComment;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IShopCommentService extends IService<ShopComment> {

    List<ShopComment> listByCommId(String commid);


    List<ShopComment> queryReply(String commid);
}
