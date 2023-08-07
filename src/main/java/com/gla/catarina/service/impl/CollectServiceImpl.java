package com.gla.catarina.service.impl;

import cn.hutool.core.date.DateUtil;
import com.gla.catarina.entity.Collect;
import com.gla.catarina.mapper.CollectMapper;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.gla.catarina.service.ICollectService;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.ResultVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 *  收藏 服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class CollectServiceImpl implements ICollectService {
    @Resource
    private CollectMapper collectMapper;

    /**添加收藏*/
    @Override
    public Integer insertCollect(Collect collect){
        return collectMapper.insertCollect(collect);
    }
    /**分页查看所有收藏内容*/
    @Override
    public List<Collect> queryAllCollect(Integer page, Integer count, String couserid){
        return collectMapper.queryAllCollect(page,count,couserid);
    }
    /**修改收藏状态*/
    @Override
    public Integer updateCollect(Collect collect){
        return collectMapper.updateCollect(collect);
    }
    /**查询商品是否被用户收藏*/
    @Override
    public Collect queryCollectStatus(Collect collect){
        return collectMapper.queryCollectStatus(collect);
    }
    /**查询我的收藏的总数*/
    @Override
    public Integer queryCollectCount(String couserid){
        return collectMapper.queryCollectCount(couserid);
    }

    @Override
    public ResultVo insertcollect(Collect collect, HttpSession session) {
        String couserid = (String) session.getAttribute("userid");
        Integer colloperate = collect.getColloperate();
        collect.setCouserid(couserid);

        if (StringUtils.isEmpty(couserid)){
            return new ResultVo(false, StatusCode.ACCESSERROR, "请先登录");
        }

        if (colloperate == 1){
            Collect collect1 = this.queryCollectStatus(collect);
            if(!StringUtils.isEmpty(collect1)){
                /**更改原来的收藏信息和状态*/
                collect1.setCommname(collect.getCommname()).setCommdesc(collect.getCommdesc()).setSchool(collect.getSchool())
                        .setSoldtime(DateUtil.date());
                Integer i = this.updateCollect(collect);
                if (i == 1){
                    return new ResultVo(true, StatusCode.OK, "收藏成功");
                }
                return new ResultVo(false, StatusCode.ERROR, "收藏失败");
            }else{
                collect.setId(KeyUtil.genUniqueKey());
                Integer i = this.insertCollect(collect);
                if (i == 1){
                    return new ResultVo(true, StatusCode.OK, "收藏成功");
                }
                return new ResultVo(false, StatusCode.ERROR, "收藏失败");
            }

        }else {
            Collect collect1 = this.queryCollectStatus(collect);
            /**判断是否为本人操作*/
            if (collect1.getCouserid().equals(couserid)){
                Integer i = this.updateCollect(collect);
                if (i == 1){
                    return new ResultVo(true, StatusCode.OK, "取消成功");
                }
                return new ResultVo(false, StatusCode.ERROR, "取消失败");
            }
            return new ResultVo(false, StatusCode.ACCESSERROR, "禁止操作");
        }
    }
}
