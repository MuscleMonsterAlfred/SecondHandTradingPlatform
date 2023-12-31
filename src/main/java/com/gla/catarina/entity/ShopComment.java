package com.gla.catarina.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author catarina
 */
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@Data
@Accessors(chain = true)//链式写法
@TableName("shop_comment")
public class ShopComment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 评论id
     */
    @TableId
	private String cid;
    /**
     * 商品id
     */
	private String commid;
    /**
     * 评论者id
     */

	private String cuserid;
    /**
     * 评论者昵称
     */
    @TableField(exist = false)
	private String cusername;
    /**
     * 评论者用户头像
     */
    @TableField(exist = false)
    private String cuimage;
    /**
     * 商品发布者id
     */
	private String spuserid;
    /**
     * 评论内容
     */
	private String content;
    /**
     * 评论时间
     */
	private Date commtime;
    /**
     * 0异常 1正常 2删除
     */
	private Integer commstatus;
    /**
     * 评论对应的回复集合
     */
    @TableField(exist = false)
    private List<ShopReply> shopReplyLsit;
}
