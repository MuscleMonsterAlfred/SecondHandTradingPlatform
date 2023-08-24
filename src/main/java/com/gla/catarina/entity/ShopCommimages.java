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
 * @since 2023-06-25
 */
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@Data
@Accessors(chain = true)//链式写法
@TableName("shop_commimages")
public class ShopCommimages implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片id
     */
	private String id;
    /**
     * 商品id
     */
	private String commid;
    /**
     * 图片
     */
	private String image;
    /**
     * 发布时间
     */
	private Date createtime;

    /**
     *  图片状态
     */
    private Integer imagestatus;
}
