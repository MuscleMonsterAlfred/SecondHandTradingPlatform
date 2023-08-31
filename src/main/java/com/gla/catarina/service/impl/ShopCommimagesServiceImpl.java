package com.gla.catarina.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopCommimages;
import com.gla.catarina.mapper.ShopCommimagesMapper;
import com.gla.catarina.service.IShopCommimagesService;
import com.gla.catarina.util.KeyUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author catarina
 */
@Service
@Transactional
public class ShopCommimagesServiceImpl extends ServiceImpl<ShopCommimagesMapper, ShopCommimages> implements IShopCommimagesService {
    @Resource
    private ShopCommimagesMapper shopCommimagesMapper;

    /**
     * 插入商品的Other图
     */
    @Override
    public void saveOtherImages(List<String> otherimg, String commid) {

        List<ShopCommimages> dataList = new ArrayList<>();
        for (String list : otherimg) {
            ShopCommimages shopCommimages = new ShopCommimages();
            shopCommimages.setCommid(commid);
            shopCommimages.setId(KeyUtil.genUniqueKey());
            shopCommimages.setImage(list);
            dataList.add(shopCommimages);
        }
        saveBatch(dataList);
    }

    /**
     * 查询某个商品得Other图
     */
    @Override
    public List<String> listByCommId(String commId) {
        LambdaQueryWrapper<ShopCommimages> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopCommimages::getCommid, commId);
        List<ShopCommimages> list = list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            List<String> collect = list.stream().map(ShopCommimages::getImage).collect(Collectors.toList());
            return collect;
        }
        return new ArrayList<>();
    }

    /**
     * 删除某个商品得Other图
     */
    @Override
    public void delByCommId(String commid) {
        LambdaQueryWrapper<ShopCommimages> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopCommimages::getCommid, commid);
        remove(queryWrapper);
    }
}
