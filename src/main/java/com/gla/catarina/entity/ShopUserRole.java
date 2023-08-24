package com.gla.catarina.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
@TableName("shop_user_role")
public class ShopUserRole implements Serializable {

    private static final long serialVersionUID = 1L;

	private String userid;
    /**
     * 1普通用户 2管理员 3超级管理员
     */
	private Integer roleid;
    /**
     * 身份
     */
	private String identity;


}
