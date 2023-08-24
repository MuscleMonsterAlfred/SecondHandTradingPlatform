package com.gla.catarina.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gla.catarina.entity.ShopNews;
import com.gla.catarina.mapper.ShopNewsMapper;
import com.gla.catarina.service.IShopNewsService;
import com.gla.catarina.util.KeyUtil;
import com.gla.catarina.util.StatusCode;
import com.gla.catarina.vo.ResultVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author catarina
 * @since 2023-06-21
 */
@Service
@Transactional
public class ShopNewsServiceImpl extends ServiceImpl<ShopNewsMapper, ShopNews> implements IShopNewsService {
    @Resource
    private ShopNewsMapper shopNewsMapper;


    /**
     * 删除公告
     */
    @Override
    public Integer delectNews(String id) {
        return shopNewsMapper.delectNews(id);
    }

    /**
     * 查看公告详情
     */
    @Override
    public ShopNews queryNewsById(String id) {
        return shopNewsMapper.queryNewsById(id);
    }

    /**
     * 浏览量
     */
    @Override
    public void addNewsRednumber(String id) {
        shopNewsMapper.addNewsRednumber(id);
    }

    /**
     * 查询前三条公告
     */
    @Override
    public List<ShopNews> queryNews() {
        return shopNewsMapper.queryNews();
    }

    /**
     * 分页展示公告信息
     */
    @Override
    public List<ShopNews> queryAllNews(@Param("page") Integer page, @Param("count") Integer count) {
        return shopNewsMapper.queryAllNews(page, count);
    }

    /**
     * 查找所有公告的总数
     */
    @Override
    public Integer LookNewsCount() {
        return shopNewsMapper.LookNewsCount();
    }

    @Override
    public ResultVo insertNews(ShopNews shopNews, HttpSession session) {
        String username = (String) session.getAttribute("username");
        shopNews.setId(KeyUtil.genUniqueKey()).setUsername(username);
        if (this.save(shopNews)) {
            return ResultVo.ok();
        }
        return ResultVo.error();
    }

    @Override
    public ResultVo delectNews(String id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        ShopNews shopNews = this.queryNewsById(id);
        if (StringUtils.isEmpty(shopNews)) {
            return new ResultVo(false, StatusCode.FINDERROR, "Not find notice");
        } else {
            /**判断是否是本人或超级管理员*/
            if (shopNews.getUsername().equals(username) || username.equals("admin")) {
                Integer i = this.delectNews(id);
                if (i == 1) {
                    return ResultVo.ok();
                }
                return ResultVo.error();
            } else {
                return new ResultVo(false, StatusCode.ACCESSERROR, "error");
            }
        }
    }

    @Override
    public String queryNewsById(String id, ModelMap modelMap) {
        this.addNewsRednumber(id);
        ShopNews shopNews = this.queryNewsById(id);
        if (StringUtils.isEmpty(shopNews)) {
            return "/error/404";
        }
        modelMap.put("news", shopNews);
        return "/common/newsdetail";
    }

    @Override
    public String toupdate(String id, ModelMap modelMap, HttpSession session) {
        String username = (String) session.getAttribute("username");
        ShopNews shopNews = this.queryNewsById(id);
        /**如果是本人则可以跳转修改*/
        if (shopNews.getUsername().equals(username)) {
            modelMap.put("qx", 1);
            modelMap.put("news", shopNews);
            return "/admin/news/updatenews";
        }
        modelMap.put("news", shopNews);
        modelMap.put("qx", 0);
        return "/admin/news/updatenews";
    }

    @Override
    public ResultVo updateNew(ShopNews shopNews) {
        if (updateById(shopNews)) {
            return ResultVo.ok();
        }
        return ResultVo.error();
    }
}
