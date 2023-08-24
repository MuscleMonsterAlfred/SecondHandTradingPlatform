package com.gla.catarina.controller;


import com.gla.catarina.entity.*;
import com.gla.catarina.service.*;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.ResultVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  评论和回复控制器
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Controller
public class ShopCommentReplyController {
    @Resource
    private IShopCommodityService shopCommodityService;

    @Resource
    private IShopCommentService shopCommentService;

    @Resource
    private IShopReplyService shopReplyService;

    @Resource
    private IShopNoticesService shopNoticesService;

    /**
     * 查询商品下的评论和回复
     * */
    @ResponseBody
    @GetMapping("/comment/query/{commid}")
    public ResultVo queryReply(@PathVariable("commid") String commId){
        List<ShopComment> shopCommentList = shopCommentService.queryReply(commId);
        return new ResultVo(true, StatusCode.OK,"Success",shopCommentList);
    }

    /**
     * 评论
     * 1.前端传入：商品id（commid）、商品发布者id（spuserid）、评论内容（content）
     * 2.session获取：评论者id（cuserid）
     * 3.过滤评论内容后，插入评论
     */
    @ResponseBody
    @PostMapping("/comment/insert")
    public ResultVo save(@RequestBody ShopComment shopComment, HttpSession session) {
        String cUserid = (String) session.getAttribute("userid");
        String content = shopComment.getContent();

        if (StringUtils.isBlank(cUserid)) {
            return new ResultVo(false,StatusCode.ACCESSERROR,"Login");
        }
        content = getContent(content);

        shopComment.setCid(KeyUtil.genUniqueKey()).setCuserid(cUserid).setContent(content);
        /**插入评论*/
        if (shopCommentService.save(shopComment)){
            /**发出评论通知消息*/
            ShopCommodity shopCommodity = shopCommodityService.getById(shopComment.getCommid());
            ShopNotices shopNotices = new ShopNotices().setId(KeyUtil.genUniqueKey()).setUserid(shopComment.getSpuserid()).setTpname("Comment")
                    .setWhys("Your product Reviewed, go and take a look <a href=/product-detail/"+ shopComment.getCommid()+" style=\"color:#08bf91\" target=\"_blank\" >"+ shopCommodity.getCommname()+"</a> ");
            shopNoticesService.saveEntity(shopNotices);
            return new ResultVo(true, StatusCode.OK,"Success");
        }
        return new ResultVo(false,StatusCode.ERROR,"Fail");
    }

    /**
     * 评论回复
     * 1.前端传入：商品id（commid）、评论id（cid）、被回复用户id（cuserid）、商品发布者id（spuserid）、评论内容（recontent）
     * 2.session获取：回复者id（ruserid）
     * 3.过滤评论回复内容后，插入评论回复
     */
    @ResponseBody
    @PostMapping("/reply/insert")
    public ResultVo insert(@RequestBody ShopReply shopReply, HttpSession session) {
        String rUserid = (String) session.getAttribute("userid");
        String recontent = shopReply.getRecontent();

        if (StringUtils.isEmpty(rUserid)) {
            return new ResultVo(false,StatusCode.ACCESSERROR,"Login");
        }

        recontent = getContent(recontent);

        shopReply.setRid(KeyUtil.genUniqueKey()).setRuserid(rUserid).setRecontent(recontent);
        /**插入回复*/
        if (shopReplyService.save(shopReply)){
            return new ResultVo(true, StatusCode.OK,"success");
        }
        return new ResultVo(false,StatusCode.ERROR,"error");
    }

    private static String getContent(String content) {
        content = content.replace("<", "&lt;");
        content = content.replace(">", "&gt;");
        content = content.replace("'", "\"");
        return content;
    }

    /**
     * 删除评论
     * 1.获取session中用户id信息
     * 2.对比用户id信息和评论者id信息：是否满足评论者本人或商品发布者
     * 3.删除评论及评论对应的回复
     */
    @ResponseBody
    @PutMapping("/comment/delete/{cid}")
    public ResultVo del(@PathVariable("cid") String cid,HttpSession session){
        String userId = (String) session.getAttribute("userid");
        ShopComment shopComment = shopCommentService.getById(cid);
        /**如果是评论者本人或者商品发布者则进行删除操作*/
        if (shopComment.getCuserid().equals(userId) || shopComment.getSpuserid().equals(userId)){
            boolean remove = shopCommentService.removeById(cid);
            shopReplyService.delByCId(new ShopReply().setCid(cid));
            if (remove){
                return new ResultVo(true, StatusCode.OK,"Success");
            }
            return new ResultVo(false, StatusCode.ERROR,"Fail");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"No perm");
    }

    /**
     * 删除评论回复
     * 1.获取session中用户id信息
     * 2.对比用户id信息和回复者id信息：是否满足评论回复者本人或商品发布者
     * 3.进行删除操作
     */
    @ResponseBody
    @PutMapping("/reply/delete/{rid}")
    public ResultVo delete(@PathVariable("rid") String rid,HttpSession session){
        String userid = (String) session.getAttribute("userid");
        ShopReply shopReply = shopReplyService.getById(rid);
        /**如果是回复者本人或者商品发布者则进行删除操作*/
        if (shopReply.getRuserid().equals(userid) || shopReply.getSpuserid().equals(userid)){
            if (shopReplyService.removeById( rid)){
                return new ResultVo(true, StatusCode.OK,"Success");
            }
            return new ResultVo(false, StatusCode.ERROR,"Fail");
        }
        return new ResultVo(false,StatusCode.ACCESSERROR,"No perm");
    }

}

