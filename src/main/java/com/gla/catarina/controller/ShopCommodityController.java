package com.gla.catarina.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.gla.catarina.entity.ShopCollect;
import com.gla.catarina.entity.ShopCommodity;
import com.gla.catarina.entity.ShopNotices;
import com.gla.catarina.entity.ShopUserInfo;
import com.gla.catarina.service.*;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.PageVo;
import com.gla.catarina.vo.R;
import com.gla.catarina.vo.ResultVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static com.gla.catarina.util.ServletUtils.getUserId;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Controller
public class ShopCommodityController {
    @Resource
    private IShopCommodityService shopCommodityService;
    @Resource
    private IShopCommimagesService shopCommimagesService;

    @Resource
    private IShopUserRoleService shopUserRoleService;
    @Resource
    private IShopUserInfoService shopUserInfoService;
    @Resource
    private IShopCollectService collectService;
    @Resource
    private IShopNoticesService shopNoticesService;

    @Value("${catarina.env.filePath}")
    private String filePath;

    /**
     * detail
     */
    @GetMapping("/user/detail")
    public String detail() {
        return "/user/product/detail";
    }

    /**
     * editPre
     */
    @GetMapping("/user/editPre/{commid}")
    public String editPre(@PathVariable("commid") String commid, ModelMap modelMap) {

        ShopCommodity shopCommodity = shopCommodityService.getById(commid);
        if (shopCommodity.getCommstatus() == 2 || shopCommodity.getCommstatus() == 4) {
            //商品已被删除或已完成交易
            return "/error/404";
        }
        String[] commons = shopCommodity.getCommon().split("、");
        if (commons.length > 0) {
            shopCommodity.setCommon(commons[0]);
        }
        if (commons.length > 1) {
            shopCommodity.setCommon2(commons[1]);
        }

        modelMap.put("goods", shopCommodity);
        modelMap.put("otherimg", shopCommimagesService.listByCommId(commid));
        return "/user/product/edit";
    }

    /**
     * edit
     */
    @PostMapping("/product/edit")
    @ResponseBody
    public String edit(@RequestBody ShopCommodity shopCommodity) {
        shopCommodity.setUpdatetime(DateUtil.date()).setCommstatus(3);
        buildCommonField(shopCommodity);
        //修改商品
        shopCommodityService.updateById(shopCommodity);
        //删除other图
        shopCommimagesService.delByCommId(shopCommodity.getCommid());

        shopCommimagesService.saveOtherImages(shopCommodity.getOtherimg(), shopCommodity.getCommid());
        saveNotice(shopCommodity, getUserId());
        return "0";
    }

    private static void buildCommonField(ShopCommodity shopCommodity) {
        StringBuilder commonStr = new StringBuilder();
        commonStr.append(shopCommodity.getCommon())
                .append("、")
                .append(shopCommodity.getCommon2());
        shopCommodity.setCommon(commonStr.toString());
    }

    private void saveNotice(ShopCommodity shopCommodity, String userId) {
        /**发出待审核系统通知*/
        ShopNotices shopNotices = new ShopNotices();
        shopNotices.setId(KeyUtil.genUniqueKey());
        shopNotices.setUserid(userId);
        shopNotices.setTpname("Wait Approve");
        StringBuilder sb = new StringBuilder();
        sb.append("Your product <a href=/product-detail/")
                .append(shopCommodity.getCommid()).append(" style=\"color:#08bf91\" target=\"_blank\" >")
                .append(shopCommodity.getCommname())
                .append("</a> waiting to approve。");

        shopNotices.setWhys(sb.toString());
        shopNoticesService.save(shopNotices);
    }

    /**
     * add
     */
    @PostMapping("/product/add")
    @ResponseBody
    public String add(@RequestBody ShopCommodity shopCommodity) {
        String userId = getUserId();
        ShopUserInfo shopUserInfo = shopUserInfoService.getByUserId(userId);

        shopCommodity.setCommid(KeyUtil.genUniqueKey());
        shopCommodity.setUserid(userId);
        shopCommodity.setSchool(shopUserInfo.getSchool());
        buildCommonField(shopCommodity);

        shopCommodityService.save(shopCommodity);

        shopCommimagesService.saveOtherImages(shopCommodity.getOtherimg(), shopCommodity.getCommid());

        saveNotice(shopCommodity, userId);
        return "0";
    }

    /**
     * productVideo
     */
    @PostMapping("/relgoods/video")
    @ResponseBody
    public R<Map<String, Object>> productVideo(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Map<String, Object> resUrl = new HashMap<>();
        String fileName = cn.hutool.core.lang.UUID.fastUUID().toString(true);
        String extName = FileUtil.extName(file.getOriginalFilename());
        String realName = fileName + "." + extName;
        String path = filePath + realName;
        file.transferTo(new File(path));
        resUrl.put("src", "/pic/" + realName);
        return R.ok(resUrl);
    }

    /**
     * Other图片
     */
    @PostMapping(value = "/relgoods/images")
    @ResponseBody
    public R<Map<String, Object>> images(@RequestParam(value = "file", required = false) MultipartFile[] files) throws IOException {

        Map<String, Object> resUrl = new HashMap<>();
        List<String> imageUrlList = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileName = cn.hutool.core.lang.UUID.fastUUID().toString(true);
            String extName = FileUtil.extName(file.getOriginalFilename());
            String realName = fileName + "." + extName;
            String path = filePath + realName;
            file.transferTo(new File(path));
            imageUrlList.add("/pic/" + realName);
        }
        resUrl.put("src", imageUrlList);
        return R.ok(resUrl);
    }

    /**
     * 商品Detail
     */
    @GetMapping("/product-detail/{commid}")
    public String product_detail(@PathVariable("commid") String commid, ModelMap modelMap) {
        String coUserId = getUserId();

        ShopCommodity shopCommodity = shopCommodityService.getById(commid);
        boolean seeFlag = false;
        if (shopCommodity.getCommstatus().equals(1)) {//如果商品正常
            seeFlag = true;
        } else if (org.apache.commons.lang3.StringUtils.isNotBlank(coUserId)) {//如果用户已登录
            Integer roleId = shopUserRoleService.getRoleId(coUserId);
            /**商品为违规状态时：本人和管理员可查看*/
            if (shopCommodity.getCommstatus().equals(0) && (shopCommodity.getUserid().equals(coUserId) || (roleId.equals(2) || roleId.equals(3)))) {
                seeFlag = true;
                /**商品为待审核状态时：本人和管理员可查看*/
            } else if (shopCommodity.getCommstatus().equals(3) && (shopCommodity.getUserid().equals(coUserId) || (roleId.equals(2) || roleId.equals(3)))) {
                seeFlag = true;
                /**商品为已售出状态时：本人和管理员可查看*/
            } else if (shopCommodity.getCommstatus().equals(4) ) {
                seeFlag = true;
            }
        }
        if (seeFlag) {
            shopCommodity.setOtherimg(shopCommimagesService.listByCommId(commid));
            /**商品浏览量+1*/
            shopCommodityService.incrReadNum(commid);
            modelMap.put("userinfo", shopUserInfoService.getById(shopCommodity.getUserid()));
            modelMap.put("gddetail", shopCommodity);
            //如果没有用户登录
            if (org.apache.commons.lang3.StringUtils.isBlank(coUserId)) {
                modelMap.put("collectstatus", 2);
            } else {
                ShopCollect collect = collectService.getByCommId(commid, coUserId);
                if (collect != null) {
                    if (collect.getCollstatus() == 2) {
                        modelMap.put("collectstatus", 2);
                    } else {
                        modelMap.put("collectstatus", 1);
                    }
                } else {
                    modelMap.put("collectstatus", 2);
                }
            }
            return "/common/product-detail";
        } else {
            return "/error/404";
        }
    }

    /**
     * searchCommodityNumber
     */
    @GetMapping("/product/search/number/{commname}")
    @ResponseBody
    public PageVo searchCommodityNumber(@PathVariable("commname") String cName) {
        Integer count = shopCommodityService.countByName(cName);
        return new PageVo(StatusCode.OK, "Success", count);
    }

    /**
     * searchCommodity
     */
    @GetMapping("/product/search/{nowPaging}/{commname}")
    @ResponseBody
    public ResultVo searchCommodity(@PathVariable("nowPaging") Integer page, @PathVariable("commname") String commname) {
        List<ShopCommodity> dataList = shopCommodityService.pageByName(page, commname);

        return getResultVo(dataList);
    }

    private ResultVo getResultVo(List<ShopCommodity> dataList) {
        if (CollectionUtil.isNotEmpty(dataList)) {
            dataList.forEach(e -> {
                /**查询商品对应的其它图片*/
                List<String> imagesList = shopCommimagesService.listByCommId(e.getCommid());
                e.setOtherimg(imagesList);
            });

            return new ResultVo(true, StatusCode.OK, "Success", dataList);
        } else {
            return new ResultVo(true, StatusCode.ERROR, "No data");
        }
    }

    /**
     * category
     */
    @ResponseBody
    @GetMapping("/index/product/{category}")
    public ResultVo category(@PathVariable("category") String category) {
        List<ShopCommodity> dataList = shopCommodityService.listByCategory(category);
        return getResultVo(dataList);
    }

    /**
     * 查询最新发布的8条商品
     */
    @ResponseBody
    @GetMapping("/product/latest")
    public ResultVo latestCommodity() {
        List<ShopCommodity> dataList = shopCommodityService.listByCategory("All");
        return getResultVo(dataList);
    }

    /**
     * number
     */
    @GetMapping("/list-number/{category}/{area}/{minmoney}/{maxmoney}")
    @ResponseBody
    public PageVo productListNumber(@PathVariable("category") String category, @PathVariable("area") String area,
                                    @PathVariable("minmoney") BigDecimal minmoney, @PathVariable("maxmoney") BigDecimal maxmoney,
                                    HttpSession session) {
        String school = null;
        if (null == area) {
            ShopUserInfo shopUserInfo = shopUserInfoService.getByUserId(getUserId());
            school = shopUserInfo.getSchool();
        }else{
            school = area;
        }

        Integer dataNumber = shopCommodityService.countAllByCategoryCount(area, school, category, minmoney, maxmoney);
        return new PageVo(StatusCode.OK, "Success", dataNumber);
    }

    /**
     * listingProduct
     */
    @GetMapping("/product-listing/{category}/{nowPaging}/{area}/{minmoney}/{maxmoney}/{price}")
    @ResponseBody
    public ResultVo listingProduct(@PathVariable("category") String category, @PathVariable("nowPaging") Integer page,
                                   @PathVariable("area") String area, @PathVariable("minmoney") BigDecimal minmoney, @PathVariable("maxmoney") BigDecimal maxmoney,
                                   @PathVariable("price") Integer price) {
        String school = null;
        if (null == area) {
            ShopUserInfo shopUserInfo = shopUserInfoService.getByUserId(getUserId());
            school = shopUserInfo.getSchool();
        }else{
            school = area;
        }

        List<ShopCommodity> dataList = shopCommodityService.listAll((page - 1) * 16, 16, area, school, category, minmoney, maxmoney);

        dataList.forEach(e -> {
            /**查询商品对应的其它图片*/
            List<String> imagesList = shopCommimagesService.listByCommId(e.getCommid());
            e.setOtherimg(imagesList);
        });

        /**自定义排序*/
        switch (price) {
            case 1:
                Collections.sort(dataList, (c1, c2) -> {//此处创建了一个匿名内部类
                    if (c1.getThinkmoney().compareTo(c2.getThinkmoney()) > -1) {
                        return 1;
                    } else {
                        return -1;
                    }
                });
                break;
            case 2:
                Collections.sort(dataList, (c1, c2) -> {//此处创建了一个匿名内部类
                    if (c1.getThinkmoney().compareTo(c2.getThinkmoney()) > -1) {
                        return -1;
                    } else {
                        return 1;
                    }
                });
                break;
            default:
                break;
        }

        return new ResultVo(true, StatusCode.OK, "Success", dataList);
    }

    /**
     * userCommodity
     */
    @GetMapping("/user/commodity/{commstatus}")
    @ResponseBody
    public LayuiPageVo userCommodity(@PathVariable("commstatus") Integer commstatus, int limit, int page) {
        String userId = getUserId();

        List<ShopCommodity> dataList;
        Integer dataNumber;
        if (commstatus == 100) {
            dataList = shopCommodityService.listByUserStatus((page - 1) * limit, limit, userId, null);
            dataNumber = shopCommodityService.countByUserStatus(userId, null);
        } else {
            dataList = shopCommodityService.listByUserStatus((page - 1) * limit, limit, userId, commstatus);
            dataNumber = shopCommodityService.countByUserStatus(userId, commstatus);
        }
        return new LayuiPageVo("", 0, dataNumber, dataList);
    }

    /**
     * ChangeStatus
     */
    @ResponseBody
    @GetMapping("/user/changecommstatus/{commid}/{commstatus}")
    public ResultVo ChangeStatus(@PathVariable("commid") String commid, @PathVariable("commstatus") Integer commstatus) {
        Integer res = shopCommodityService.updateStatus(commid, commstatus);
        if (res > 0) {
            return ResultVo.ok();
        }
        return ResultVo.error();
    }
}

