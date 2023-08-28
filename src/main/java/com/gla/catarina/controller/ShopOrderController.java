package com.gla.catarina.controller;

import com.gla.catarina.entity.ShopCommodity;
import com.gla.catarina.entity.ShopNotices;
import com.gla.catarina.entity.ShopOrders;
import com.gla.catarina.service.IShopCommodityService;
import com.gla.catarina.service.IShopNoticesService;
import com.gla.catarina.service.IShopOrdersService;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.ThreadUtil;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.gla.catarina.util.ServletUtils.getUserId;

@Controller
public class ShopOrderController {
    @Resource
    IShopOrdersService shopOrdersService;
    @Resource
    IShopCommodityService shopCommodityService;
    @Resource
    private IShopNoticesService shopNoticesService;

    /**
     * jump
     */
    @GetMapping("/shop/addressinfo/{shopid}")
    public ModelAndView jump(@PathVariable("shopid") String shopid) {
        ModelAndView mv = new ModelAndView("/alipay/addressinfo");
        mv.addObject("shopid", shopid);
        return mv;
    }

    /**
     * delivery
     */
    @PostMapping("/order/kdnumber")
    @ResponseBody
    public ResultVo delivery(@RequestBody ShopOrders order) {
        order.setKdstatus(1);
        shopOrdersService.updateOrderByNumber(order);
        /**
         * 详情
         * */
        ShopOrders shopOrders = shopOrdersService.getByNumber(order.getOrdernumber());
        saveNotice(shopOrders);
        return ResultVo.ok();
    }

    private void saveNotice(ShopOrders shopOrders) {
        /**Send Notice*/
        ShopNotices shopNotices = new ShopNotices();
        shopNotices.setId(KeyUtil.genUniqueKey());
        shopNotices.setUserid(shopOrders.getBuyuserid());
        shopNotices.setTpname("System Notice");
        StringBuilder sb = new StringBuilder();
        sb.append("The item you purchased has been shipped. Please check the logistics information carefully <a href=/product-detail/")
                .append(shopOrders.getCommid()).append(" style=\"color:#08bf91\" target=\"_blank\" >")
                .append(shopOrders.getCommname())
                .append("</a> ");

        shopNotices.setWhys(sb.toString());

        //Asynchronous Notification
        ThreadUtil.run(()->{
            shopNoticesService.save(shopNotices);
        });
    }

    /**
     * toPay
     *
     * @param order
     * @return
     */
    @PutMapping("/shop/topay")
    @ResponseBody
    public ResultVo toPay(@RequestBody ShopOrders order) {
        ShopCommodity shopCommodity = shopCommodityService.getById(order.getCommid());//查询商品详情
        if(1 != shopCommodity.getCommstatus()){
            return ResultVo.error("Sold");
        }
        order.setId(KeyUtil.genUniqueKey());
        order.setOrdernumber(KeyUtil.genUniqueKey());
        order.setSelluserid(shopCommodity.getUserid());
        order.setBuyuserid(getUserId());
        order.setKdnumber("Wait");
        order.setKdstatus(0);
        order.setOrderstatus(0);
        order.setPrice(shopCommodity.getThinkmoney());
        order.setCommnumber(1);
        order.setOrdertime(new Date());
        order.setCommname(shopCommodity.getCommname());
        order.setCommdesc(shopCommodity.getCommdesc());

        shopOrdersService.save(order);
        //更新商品为已出售
        shopCommodityService.updateStatus(order.getCommid(), 4);
        return ResultVo.okData(order.getOrdernumber());
    }

    /**
     * payOrder
     *
     * @param orderNum
     * @return
     */
    @GetMapping("/shop/payOrder")
    public ModelAndView payOrder(String orderNum) {
        ModelAndView mv = new ModelAndView("/alipay/alipaySuccess");
        ShopOrders shopOrders = shopOrdersService.getByNumber(orderNum);

        ShopOrders param = new ShopOrders();
        param.setOrdernumber(orderNum);
        param.setOrderstatus(1);
        shopOrdersService.updateOrderByNumber(param);

        mv.addObject("orders", shopOrders);
        return mv;
    }

    /**
     * detail
     *
     * @param ordernum
     * @return
     */
    @GetMapping("/shop/preorder/{ordernum}")
    public ModelAndView detail(@PathVariable("ordernum") String ordernum) {
        ModelAndView mv = new ModelAndView("/alipay/previeworder");
        mv.addObject("orders", shopOrdersService.getByNumber(ordernum));
        return mv;
    }


    /**
     * listSold
     */
    @ResponseBody
    @GetMapping("/orderecord/lookuser")
    public LayuiPageVo listSold(int limit, int page) {
        int current = (page - 1) * limit;
        List<ShopOrders> dataList = shopOrdersService.listOrder(current, limit, getUserId());
        return new LayuiPageVo("", 0, shopOrdersService.countByUserId(getUserId()), dataList);
    }

    /**
     * allData
     */
    @ResponseBody
    @GetMapping("/orderecord/queryall")
    public LayuiPageVo allData(int limit, int page) {
        int current = (page - 1) * limit;
        List<ShopOrders> dataList = shopOrdersService.listOrder(current, limit, null);
        return new LayuiPageVo("", 0, shopOrdersService.countByUserId(null), dataList);
    }

}
