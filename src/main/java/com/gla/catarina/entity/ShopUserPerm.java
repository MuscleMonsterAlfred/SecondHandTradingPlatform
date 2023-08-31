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
 */
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@Data
@Accessors(chain = true)//链式写法
@TableName("shop_user_perm")
public class ShopUserPerm implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 1普通用户 2管理员 3超级管理员
     */
	private Integer roleid;
    /**
     * 对应权限
     */
	private String perms;
    /**
     * 权限解释
     */
	private String mean;


}
