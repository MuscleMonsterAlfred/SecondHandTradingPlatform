package com.gla.catarina.service;

import com.gla.catarina.entity.ShopLogin;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;

import javax.servlet.http.HttpSession;

/**
 * @author catarina
 */
public interface IShopAdminService {
    ResultVo adminlogin(ShopLogin shopLogin, HttpSession session);

    LayuiPageVo userlist(int limit, int page, Integer roleid, Integer userstatus);

    ResultVo setadmin(String userid, Integer roleid);

    ResultVo adminuserlist(String userid, Integer userstatus);

    LayuiPageVo userCommodity(Integer commstatus, int limit, int page);

    ResultVo changeCommstatus(String commid, Integer commstatus);
}
