package com.gla.catarina.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@TableName("shop_login")
public class ShopLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登录id
     */
    @TableId
	private String id;
    /**
     * 用户id
     */
	private String userid;
    /**
     * 角色id 1普通用户 2管理员 3超级管理员
     */
	private Integer roleid;
    /**
     * 用户名
     */
	private String username;
    /**
     * 用户密码
     */
	private String password;
    /**
     * 手机号
     */
    private String mobilephone;
    /**
     * 1正常 0封号
     */
	private Integer userstatus;
    /**
     * 验证码
     * */
    @TableField(exist = false)
    private String vercode;
    /**
     * 邮箱
     */
    private String email;
}
