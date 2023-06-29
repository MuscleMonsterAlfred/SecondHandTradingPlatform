package com.gla.catarina.controller;

import com.gla.catarina.service.NoticesService;
import com.gla.catarina.service.OrdersService;
import com.gla.catarina.entity.Notices;
import com.gla.catarina.entity.Orders;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  售出记录控制器
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Controller
public class SoldrecordController {

    @Resource
    private OrdersService ordersService;
    @Resource
    private NoticesService noticesService;

    /**
     * 确认收货
     * 1.前端传入需修改记录的id（id）
     * */
    @ResponseBody
    @PutMapping("/soldrecord/change/{ordernumber}")
    public ResultVo delectSold (@PathVariable("ordernumber") String ordernumber) {
        ordersService.ChangeOrder(new Orders().setOrdernumber(ordernumber).setKdstatus(2));

        /**查询订单详情*/
        Orders orders = ordersService.LookOrderDetail(ordernumber);
        /**给卖家发送订单通知*/
        Notices notices1 = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(orders.getSelluserid()).setTpname("系统通知")
                .setWhys("您售出的商品 <a href=/product-detail/"+orders.getCommid()+" style=\"color:#08bf91\" target=\"_blank\" >"+orders.getCommname()+"</a> 已经被收货啦。");
        noticesService.insertNotices(notices1);

        return new ResultVo(true, StatusCode.OK,"操作成功");
    }

    /**
     * 分页查看用户所有售出记录
     * 1.前端传入页码、分页数量
     * 2.查询分页数据
     */
    @ResponseBody
    @GetMapping("/soldrecord/lookuser")
    public LayuiPageVo LookUserSold(int limit, int page, HttpSession session) {
        String selluserid = (String) session.getAttribute("userid");
        //如果未登录，给一个假id
        if(StringUtils.isEmpty(selluserid)){
            selluserid = "123456";
        }
        List<Orders> soldrecordList = ordersService.queryAllSoldrecord((page - 1) * limit, limit, selluserid);
        Integer dataNumber = ordersService.querySoldCount(selluserid);
        return new LayuiPageVo("",0,dataNumber,soldrecordList);
    }

    /**
     * 分页查看全部的售出记录
     * 1.前端传入页码、分页数量
     * 2.查询分页数据
     */
    @ResponseBody
    @GetMapping("/soldrecord/queryall")
    public LayuiPageVo queryAllSold(int limit, int page) {
        List<Orders> soldrecordList = ordersService.queryAllSoldrecord((page - 1) * limit, limit, null);
        Integer dataNumber = ordersService.querySoldCount(null);
        return new LayuiPageVo("",0,dataNumber,soldrecordList);
    }

}

