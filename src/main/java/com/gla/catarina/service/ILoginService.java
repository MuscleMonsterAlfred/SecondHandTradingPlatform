package com.gla.catarina.service;

import com.gla.catarina.entity.Login;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface ILoginService {
    Integer loginAdd(Login login);

    Login userLogin(Login login);

    Integer updateLogin(Login login);
}
