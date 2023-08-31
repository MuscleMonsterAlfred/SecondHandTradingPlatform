package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopNotices;

import java.util.List;

/**
 * @author catarina
 */
public interface IShopNoticesService extends IService<ShopNotices> {
    Integer saveEntity(ShopNotices shopNotices);

    Boolean hasRead(String id);

    List<ShopNotices> listTen(String userid);

    Boolean cancel(String userid);

    List<ShopNotices> page(Integer page, Integer count, String userid);

    Integer count(String userid);
}
