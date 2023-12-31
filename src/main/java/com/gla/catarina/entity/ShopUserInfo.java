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
 */
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@Data
@Accessors(chain = true)//链式写法
@TableName("shop_user_info")
public class ShopUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "userid")
	private String userid;
    /**
     * 角色id 1普通用户 2管理员 3超级管理员
     */
    @TableField(exist = false)
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
     * 用户邮箱
     */
	private String email;
    /**
     * 用户头像
     */
	private String uimage;
    /**
     * 用户性别
     */
	private String sex;
    /**
     * 学校
     */
	private String school;
    /**
     * 院系
     */
	private String faculty;
    /**
     * 入学时间
     */
	private String startime;
    /**
     * 1正常 0封号
     */
	private Integer userstatus;
    /**
     * 注册时间
     */
	private Date createtime;
    /**
     * 验证码
     */
    @TableField(exist = false)
	private String vercode;
    /**
     * 在线状态
     */
    private String status;//在线状态 online：在线、hide：隐身

    //补充的属性
    @TableField(exist = false)
    private String id; //我的ID
    @TableField(exist = false)
    private String sign; //我的签名
    @TableField(exist = false)
    private String avatar;//我的头像
    @TableField(exist = false)
    private String content;   //聊天内容
    @TableField(exist = false)
    private String type; //消息类型
    @TableField(exist = false)
    private String toid; //聊天窗口的选中的用户或者群组的id
    @TableField(exist = false)
    private Date sendtime;  //消息发送时间
}
