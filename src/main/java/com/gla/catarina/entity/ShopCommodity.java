package com.gla.catarina.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("shop_commodity")
public class ShopCommodity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "commid", type = IdType.AUTO)
    private String commid;
    /**
     * 商品名
     */
    private String commname;
    /**
     * 商品描述
     */
    private String commdesc;
    /**
     * 视频
     */
    private String videourl;
    /**
     * 原价
     */
    private BigDecimal orimoney;
    /**
     * 售价
     */
    private BigDecimal thinkmoney;
    /**
     * 商品所在学校
     */
    private String school;
    /**
     * 发布时间
     */
    private Date createtime;
    /**
     * 修改时间
     */
    private Date updatetime;
    /**
     * 结束时间
     */
    private Date endtime;
    /**
     * 0违规 1正常 2删除  3待审核
     */
    private Integer commstatus;
    /**
     * 常用选项：自提，Bargaining，No bargaining等选项
     */
    private String common;
    /**
     * 常用类别字段
     */
    @TableField(exist = false)
    private String common2;
    /**
     * 商品Other图集合
     */
    @TableField(exist = false)
    private List<String> otherimg;

    /**
     * 浏览量
     */
    private Integer rednumber;
    /**
     * 商品类别
     */
    private String category;
    /**
     * 简介图
     */
    private String image;
    /**
     * 用户id
     */
    private String userid;

    private String goodposition;
}
