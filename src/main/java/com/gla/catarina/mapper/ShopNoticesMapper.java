package com.gla.catarina.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gla.catarina.entity.ShopNotices;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  消息通知Mapper 接口
 * </p>
 *
 * @author catarina
 * @since 2023-06-25
 */
public interface ShopNoticesMapper extends BaseMapper<ShopNotices> {


    List<ShopNotices> listTen(String userid);

    List<ShopNotices> page(@Param("page") Integer page, @Param("count") Integer count, @Param("userid") String userid);

}
