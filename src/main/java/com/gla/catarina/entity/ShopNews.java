package com.gla.catarina.entity;

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
 */
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@Data
@Accessors(chain = true)//链式写法
@TableName("shop_news")
public class ShopNews implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 新闻id
     */
	private String id;
    /**
     * 新闻标题
     */
	private String newstitle;
    /**
     * 新闻简介
     */
	private String newsdesc;
    /**
     * 新闻内容
     */
	private String newscontent;
    /**
     * 发布时间
     */
	private Date createtime;
    /**
     * 新闻发布者
     */
	private String username;
    /**
     * 图片
     */
    private String image;
    /**
     * 1正常  2删除
     */
	private Integer newsstatus;
    /**
     * 浏览量
     */
	private Integer rednumber;


}
