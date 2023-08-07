package com.gla.catarina.service;

import com.gla.catarina.entity.Login;
import com.gla.catarina.vo.LayuiPageVo;
import com.gla.catarina.vo.ResultVo;

import javax.servlet.http.HttpSession;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IAdminService {
    ResultVo adminlogin(Login login, HttpSession session);

    LayuiPageVo userlist(int limit, int page, Integer roleid, Integer userstatus);

    ResultVo setadmin(String userid, Integer roleid);

    ResultVo adminuserlist(String userid, Integer userstatus);

    LayuiPageVo userCommodity(Integer commstatus, int limit, int page);

    ResultVo changeCommstatus(String commid, Integer commstatus);
}
