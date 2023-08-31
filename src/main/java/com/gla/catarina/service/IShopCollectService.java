package com.gla.catarina.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gla.catarina.entity.ShopCollect;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;

/**
 * @author catarina
 */
public interface IShopCollectService extends IService<ShopCollect> {

    LayuiPageVo page(Integer page, Integer pageSize, String userId);

    ResultVo insert(ShopCollect collect);

    ShopCollect queryById(String id, String userId);

    ShopCollect getByCommId(String commid, String couserid);
}
