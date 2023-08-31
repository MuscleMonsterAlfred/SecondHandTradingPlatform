package com.gla.catarina.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopCollect;
import com.gla.catarina.mapper.ShopCollectMapper;
import com.gla.catarina.service.IShopCollectService;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 收藏 服务类
 * </p>
 *
 * @author catarina
 */
@Service
@Transactional
public class ShopCollectServiceImpl extends ServiceImpl<ShopCollectMapper, ShopCollect> implements IShopCollectService {
    @Resource
    private ShopCollectMapper shopCollectMapper;

    /**
     * 分页查看所有收藏内容
     */
    @Override
    public LayuiPageVo page(Integer page, Integer pageSize, String userId) {
        List<ShopCollect> shopCollects = shopCollectMapper.queryAllCollect(page, pageSize, userId);
        LambdaQueryWrapper<ShopCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopCollect::getCouserid, userId);
        long count = count(queryWrapper);
        return new LayuiPageVo("", 0, (int) count, shopCollects);
    }

    @Override
    public ShopCollect queryById(String id, String userId) {
        LambdaQueryWrapper<ShopCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopCollect::getId, id);
        queryWrapper.eq(ShopCollect::getCmuserid, userId);
        return getOne(queryWrapper);
    }

    @Override
    public ShopCollect getByCommId(String commid, String couserid) {
        LambdaQueryWrapper<ShopCollect> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopCollect::getCommid, commid);
        queryWrapper.eq(ShopCollect::getCouserid, couserid);
        return getOne(queryWrapper);
    }

    @Override
    public ResultVo insert(ShopCollect collect) {
        ;
        Integer colloperate = collect.getColloperate();

        if (StringUtils.isBlank(collect.getCouserid())) {
            return new ResultVo(false, StatusCode.ACCESSERROR, "Login");
        }

        ShopCollect entity = this.getByCommId(collect.getCommid(), collect.getCouserid());

        if (colloperate == 1) {
            if (null == entity) {
                /**更改原来的收藏信息和状态*/
                collect.setSoldtime(DateUtil.date());
                if (this.save(collect)) {
                    return new ResultVo(true, StatusCode.OK, "Success");
                }
            }
        } else {
            if (removeById(entity.getId())) {
                return new ResultVo(true, StatusCode.OK, "Success");
            }
            return new ResultVo(false, StatusCode.ERROR, "Fail");

        }
        return new ResultVo(false, StatusCode.ACCESSERROR, "Success");
    }
}
