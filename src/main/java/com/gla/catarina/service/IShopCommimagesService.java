package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopCommimages;

import java.util.List;

/**
 * @author catarina
 */
public interface IShopCommimagesService extends IService<ShopCommimages> {
    void saveOtherImages(List<String> otherimg, String commid);

    List<String> listByCommId(String commId);

    void delByCommId(String commid);
}
