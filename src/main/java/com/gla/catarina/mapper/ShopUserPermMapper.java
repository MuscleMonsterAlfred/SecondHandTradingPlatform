package com.gla.catarina.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gla.catarina.entity.ShopUserPerm;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
public interface ShopUserPermMapper extends BaseMapper<ShopUserPerm> {
    /**查询用户的权限*/
    List<String> LookPermsByUserid(Integer roleid);
}
