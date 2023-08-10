package com.gla.catarina.controller;

import com.gla.catarina.service.ICommodityService;
import com.gla.catarina.service.INoticesService;
import com.gla.catarina.service.IOrdersService;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.gla.catarina.entity.Commodity;
import com.gla.catarina.entity.Notices;
import com.gla.catarina.entity.Orders;
import com.gla.catarina.util.AlipayConfig;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class OrderController {
    @Resource
    IOrdersService IOrdersService;
    @Resource
    ICommodityService ICommodityService;
    @Resource
    private INoticesService INoticesService;

    /**
     * 跳转输入收货地址页面
     * @param shopid
     * @param modelMap
     * @return
     */
    @GetMapping("/shop/addressinfo/{shopid}")
    public String toaddaddressinfo(@PathVariable("shopid")String shopid, ModelMap modelMap){
        modelMap.put("shopid",shopid);
        return "/alipay/addressinfo";
    }

    /**
     * 确认发货
     * @param order
     * @return
     */
    @PostMapping("/order/kdnumber")
    @ResponseBody
    public ResultVo addkdnumber(@RequestBody Orders order){
        order.setKdstatus(1);
        IOrdersService.ChangeOrder(order);
        /**查询订单详情*/
        Orders orders = IOrdersService.LookOrderDetail(order.getOrdernumber());
        /**给买家发送订单通知*/
        Notices notices1 = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(orders.getBuyuserid()).setTpname("System Notice")
                .setWhys("The item you purchased has been shipped. Please check the logistics information carefully <a href=/product-detail/"+orders.getCommid()+" style=\"color:#08bf91\" target=\"_blank\" >"+orders.getCommname()+"</a> ");
        INoticesService.insertNotices(notices1);
        return new ResultVo(false, StatusCode.OK,"success");
    }



    /**
     * 用户提交收回地址接口
     * @param order
     * @param session
     * @return
     */
    @PutMapping("/shop/topay")
    @ResponseBody public ResultVo topay(@RequestBody Orders order, HttpSession session){
        String userid=(String)session.getAttribute("userid");
        String id= KeyUtil.genUniqueKey();//订单id
        String ordernumber=KeyUtil.genUniqueKey();//订单编号
        Commodity commodity = ICommodityService.LookCommodity(new Commodity().setCommid(order.getCommid()));//查询商品详情
        order.setCommdesc(commodity.getCommdesc()).setCommname(commodity.getCommname()).setOrdertime(new Date()).
                setCommnumber(1).setPrice(commodity.getThinkmoney()).setOrderstatus(0).setKdstatus(0).setKdnumber("待发货")
            .setBuyuserid(userid).setSelluserid(commodity.getUserid()).setOrdernumber(ordernumber).setId(id);
        IOrdersService.InsertOrder(order);
        return new ResultVo(false, StatusCode.OK,"正在跳转到支付界面",ordernumber);
    }

    /**
     * 跳转到订单预览界面
     * @param ordernum
     * @param modelMap
     * @return
     */
    @GetMapping("/shop/preorder/{ordernum}")
    public String toprevieworder(@PathVariable("ordernum")String ordernum, ModelMap modelMap){
        modelMap.put("orders", IOrdersService.LookOrderDetail(ordernum));
        return "/alipay/previeworder";
    }

    /**
     * 去支付
     * @param orderid
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/alipay/gopay", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String gopay(String orderid) throws Exception {
        Orders orders= IOrdersService.LookOrderDetail(orderid);
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderid;
        //付款金额，必填
        String total_amount = orders.getPrice().toString();
        //订单名称，必填
        String subject = orders.getCommname();
        //商品描述，可空
        String body = "用户订购商品个数：1";

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "30m";

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = alipayClient.pageExecute(alipayRequest).getBody();
        return result;
    }

    @RequestMapping(value = "/alipay/alipayReturnNotice")
    public String alipayReturnNotice(HttpServletRequest request, ModelMap model) throws Exception {
        System.out.println("Springboot支付成功");
        //商户订单号
        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        IOrdersService.ChangeOrder(new Orders().setOrdernumber(out_trade_no).setOrderstatus(1));

        /**查询订单详情*/
        Orders orders = IOrdersService.LookOrderDetail(out_trade_no);
        /**给买家发送订单通知*/
        Notices notices1 = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(orders.getBuyuserid()).setTpname("System Notice")
                .setWhys(" <a href=/product-detail/"+orders.getCommid()+" style=\"color:#08bf91\" target=\"_blank\" >"+orders.getCommname()+"</a> Please check the logistics information at any time。");
        INoticesService.insertNotices(notices1);

        /**给卖家发送订单通知*/
        Notices notices2 = new Notices().setId(KeyUtil.genUniqueKey()).setUserid(orders.getSelluserid()).setTpname("System Notice")
                .setWhys(" <a href=/product-detail/"+orders.getCommid()+" style=\"color:#08bf91\" target=\"_blank\" >"+orders.getCommname()+"</a> purchased, please ship in a timely manner。");
        INoticesService.insertNotices(notices2);

        return "/alipay/alipaySuccess";
    }


    @RequestMapping(value = "/alipay/alipayNotifyNotice")
    @ResponseBody
    public String alipayNotifyNotice(HttpServletRequest request) throws Exception {

        System.out.println("支付成功, 进入异步通知接口...");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

		/* 实际验证过程建议商户务必添加以下校验：
		1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
		2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
		3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
		4、验证app_id是否为该商户本身。
		*/
        if(signVerified) {//验证成功
            //商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

            //交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

            //付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意： 尚自习的订单没有退款功能, 这个条件判断是进不来的, 所以此处不必写代码
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知

            }
            System.out.println("支付成功...");

        }else {//验证失败
            System.out.println("支付, 验签失败...");
        }

        return "success";
    }

    /**
     * 分页查看用户所有订单记录
     * 1.前端传入页码、分页数量
     * 2.查询分页数据
     */
    @ResponseBody
    @GetMapping("/orderecord/lookuser")
    public LayuiPageVo LookUserSold(int limit, int page, HttpSession session) {
        String buyuserid = (String) session.getAttribute("userid");
        List<Orders> soldrecordList = IOrdersService.queryAllOrderecord((page - 1) * limit, limit, buyuserid);
        Integer dataNumber = IOrdersService.queryOrderCount(buyuserid);
        return new LayuiPageVo("",0,dataNumber,soldrecordList);
    }

    /**
     * 分页查看全部的订单记录
     * 1.前端传入页码、分页数量
     * 2.查询分页数据
     */
    @ResponseBody
    @GetMapping("/orderecord/queryall")
    public LayuiPageVo queryAllSold(int limit, int page) {
        List<Orders> soldrecordList = IOrdersService.queryAllOrderecord((page - 1) * limit, limit, null);
        Integer dataNumber = IOrdersService.queryOrderCount(null);
        return new LayuiPageVo("",0,dataNumber,soldrecordList);
    }

}
