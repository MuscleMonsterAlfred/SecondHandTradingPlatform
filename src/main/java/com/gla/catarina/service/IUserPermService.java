package com.gla.catarina.service;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface IUserPermService {
    //查询用户的权限
    List<String> LookPermsByUserid(Integer id);
}
