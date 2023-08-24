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

/**
 * <p>
 * 
 * </p>
 *
 * @author catarina
 * @since 2023-06-25
 */
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@Data
@Accessors(chain = true)//链式写法
@TableName("shop_reply")
public class ShopReply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 回复id
     */
    @TableId
	private String rid;
    /**
     * 评论id
     */
	private String cid;
    /**
     * 商品id
     */
	private String commid;
    /**
     * 被回复用户id
     */
	private String cuserid;
    /**
     * 被回复者昵称
     */
    @TableField(exist = false)
    private String cusername;
    /**
     * 商品发布者id
     */
	private String spuserid;
    /**
     * 回复内容
     */
	private String recontent;
    /**
     * 回复者id
     */
	private String ruserid;
    /**
     * 回复者昵称
     */
    @TableField(exist = false)
    private String rusername;
    /**
     * 回复者用户头像
     */
    @TableField(exist = false)
    private String ruimage;
    /**
     * 回复时间
     */
	private Date replytime;
    /**
     * 0异常 1正常 2删除
     */
	private Integer repstatus;


}
