package com.gla.catarina.controller;

import com.gla.catarina.entity.ShopNotices;
import com.gla.catarina.entity.ShopOrders;
import com.gla.catarina.service.IShopNoticesService;
import com.gla.catarina.service.IShopOrdersService;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static com.gla.catarina.util.ServletUtils.getUserId;

/**
 * <p>
 * 售出记录
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Controller
public class SoldrecordController {

    @Resource
    private IShopOrdersService shopOrdersService;
    @Resource
    private IShopNoticesService shopNoticesService;

    /**
     * confirmReceive
     */
    @ResponseBody
    @PutMapping("/soldrecord/change/{ordernumber}")
    public ResultVo confirmReceive(@PathVariable("ordernumber") String ordernumber) {
        ShopOrders order = new ShopOrders();
        order.setOrdernumber(ordernumber);
        order.setKdstatus(2);
        shopOrdersService.updateOrderByNumber(order);

        /**detail*/
        ShopOrders shopOrders = shopOrdersService.getByNumber(ordernumber);
        saveNotice(shopOrders);

        return ResultVo.ok();
    }

    private void saveNotice(ShopOrders shopOrders) {
        /**发出待审核系统通知*/
        ShopNotices shopNotices = new ShopNotices();
        shopNotices.setId(KeyUtil.genUniqueKey());
        shopNotices.setUserid(shopOrders.getBuyuserid());
        shopNotices.setTpname("System Notice");
        StringBuilder sb = new StringBuilder();
        sb.append("The product you sold <a href=/product-detail/")
                .append(shopOrders.getCommid()).append(" style=\"color:#08bf91\" target=\"_blank\" >")
                .append(shopOrders.getCommname())
                .append("</a> ,The goods have been received");

        shopNotices.setWhys(sb.toString());
        shopNoticesService.save(shopNotices);
    }

    /**
     * list
     */
    @ResponseBody
    @GetMapping("/soldrecord/lookuser")
    public LayuiPageVo list(int limit, int page) {
        int current = (page - 1) * limit;
        List<ShopOrders> dataList = shopOrdersService.listAllSell(current, limit, getUserId());
        return new LayuiPageVo("", 0, shopOrdersService.countAllSell(getUserId()), dataList);
    }

    /**
     * listAllSold
     */
    @ResponseBody
    @GetMapping("/soldrecord/queryall")
    public LayuiPageVo listAllSold(int limit, int page) {
        int current = (page - 1) * limit;
        List<ShopOrders> dataList = shopOrdersService.listAllSell(current, limit, null);
        return new LayuiPageVo("", 0, shopOrdersService.countAllSell(null), dataList);
    }

}

