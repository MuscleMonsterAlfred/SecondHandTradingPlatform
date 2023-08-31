package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopComment;

import java.util.List;

/**
 * @author catarina
 */
public interface IShopCommentService extends IService<ShopComment> {

    List<ShopComment> listByCommId(String commid);


    List<ShopComment> queryReply(String commid);
}
