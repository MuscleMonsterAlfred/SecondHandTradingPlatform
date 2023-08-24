package com.gla.catarina.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gla.catarina.entity.ShopUserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
public interface ShopUserInfoMapper extends BaseMapper<ShopUserInfo> {

    /**分页查询不同角色用户信息*/
    List<ShopUserInfo> queryAllUserInfo(@Param("page") Integer page, @Param("count") Integer count, @Param("roleid") Integer roleid, @Param("userstatus") Integer userstatus);
    /**查看不同角色用户总数*/
    Integer queryAllUserCount(Integer roleid);

}
