package com.gla.catarina.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author catarina
 * @since 2021-03-06
 */
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@Data
@Accessors(chain = true)//链式写法
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
	private String id;
    /**
     * 订单编号
     */
	private String ordernumber;
    /**
     * 下单时间
     */
	private Date ordertime;
    /**
     * 商品名
     */
	private String commname;
    /**
     * 商品id
     */
    private String commid;
    /**
     * 商品描述
     */
    private String commdesc;
    /**
     * 购买数量
     */
	private Integer commnumber;
    /**
     * 商品单价
     */
	private BigDecimal price;
    /**
     * 收货地址
     */
	private String useraddress;
    /**
     * 订单状态 0未支付 1正常 2删除
     */
	private Integer orderstatus;
    /**
     * 收货人
     */
	private String username;
    /**
     * 收货人手机号
     */
	private String mobilephone;
    /**
     * 发货状态 0未发货 1已发货 2确认收货
     */
	private Integer kdstatus;
    /**
     * 快递编号
     */
	private String kdnumber;
    /**
     * 买家id
     */
	private String buyuserid;
    /**
     * 卖家id
     */
	private String selluserid;
}
