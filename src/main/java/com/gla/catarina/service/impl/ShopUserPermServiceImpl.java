package com.gla.catarina.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopUserPerm;
import com.gla.catarina.mapper.ShopUserPermMapper;
import com.gla.catarina.service.IShopUserPermService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class ShopUserPermServiceImpl extends ServiceImpl<ShopUserPermMapper, ShopUserPerm> implements IShopUserPermService {
    @Resource
    private ShopUserPermMapper shopUserPermMapper;

    //查询用户的权限
    @Override
    public List<String> LookPermsByUserid(Integer id){
        return shopUserPermMapper.LookPermsByUserid(id);
    }
}
