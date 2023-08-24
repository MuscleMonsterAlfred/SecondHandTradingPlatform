package com.gla.catarina.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopComment;
import com.gla.catarina.entity.ShopReply;
import com.gla.catarina.entity.ShopUserInfo;
import com.gla.catarina.mapper.ShopCommentMapper;
import com.gla.catarina.service.IShopCommentService;
import com.gla.catarina.service.IShopReplyService;
import com.gla.catarina.service.IShopUserInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class ShopCommentServiceImpl extends ServiceImpl<ShopCommentMapper, ShopComment> implements IShopCommentService {
    @Resource
    private ShopCommentMapper shopCommentMapper;

    @Resource
    private IShopReplyService shopReplyService;

    @Resource
    private IShopUserInfoService shopUserInfoService;


    /**
     * 查询评论
     */
    @Override
    public List<ShopComment> listByCommId(String commid) {
        LambdaQueryWrapper<ShopComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopComment::getCommid, commid);
        return list(queryWrapper);
    }


    @Override
    public List<ShopComment> queryReply(String commId) {
        List<ShopComment> shopCommentList = this.listByCommId(commId);
        shopCommentList.forEach(e -> {
            /**查询对应评论下的回复*/
            List<ShopReply> repliesList = shopReplyService.listByCId(e.getCid());
            repliesList.forEach(r -> {
                /**查询回复者的昵称和头像信息*/
                ShopUserInfo userInfo = shopUserInfoService.getById(r.getRuserid());
                /**查询被回复者的昵称信息*/
                ShopUserInfo userInfo1 = shopUserInfoService.getById(r.getCuserid());
                /**添加回复中涉及到的用户昵称及头像信息*/
                r.setRusername(userInfo.getUsername()).setRuimage(userInfo.getUimage()).setCusername(userInfo1.getUsername());
            });

            /**查询评论者的昵称和头像信息*/
            ShopUserInfo shopUserInfo = shopUserInfoService.getById(e.getCuserid());
            /**添加评论下的回复及评论者昵称和头像信息*/
            e.setShopReplyLsit(repliesList).setCusername(shopUserInfo.getUsername()).setCuimage(shopUserInfo.getUimage());
        });

        return shopCommentList;
    }
}
