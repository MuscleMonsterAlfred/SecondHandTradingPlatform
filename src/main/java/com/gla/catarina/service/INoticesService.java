package com.gla.catarina.service;

import com.gla.catarina.entity.Notices;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface INoticesService {
    Integer insertNotices(Notices notices);

    Integer updateNoticesById(String id);

    List<Notices> queryNotices(String userid);

    Integer CancelLatest(String userid);

    List<Notices> queryAllNotices(Integer page, Integer count, String userid);

    Integer queryNoticesCount(String userid);
}
