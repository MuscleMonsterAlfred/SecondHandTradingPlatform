package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopLogin;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IShopLoginService extends IService<ShopLogin> {
    Integer saveLogin(ShopLogin shopLogin);

    ShopLogin getUserInfo(ShopLogin shopLogin);

    Integer updateEntity(ShopLogin shopLogin);
}
