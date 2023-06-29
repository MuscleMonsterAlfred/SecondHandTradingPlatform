package com.gla.catarina.mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
public interface UserPermMapper {
    /**查询用户的权限*/
    List<String> LookPermsByUserid(Integer roleid);
}
