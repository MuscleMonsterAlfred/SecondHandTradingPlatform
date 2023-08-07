package com.gla.catarina.service;

import com.gla.catarina.entity.Collect;
import com.gla.catarina.vo.ResultVo;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface ICollectService {
    Integer insertCollect(Collect collect);

    List<Collect> queryAllCollect(Integer page, Integer count, String couserid);

    Integer updateCollect(Collect collect);

    Collect queryCollectStatus(Collect collect);

    Integer queryCollectCount(String couserid);

    ResultVo insertcollect(Collect collect, HttpSession session);
}
