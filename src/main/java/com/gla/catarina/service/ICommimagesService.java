package com.gla.catarina.service;

import com.gla.catarina.entity.Commimages;

import java.util.List;

/**
 * @author catarina
 * @since 2023-06-21
 */
public interface ICommimagesService {
    void InsertGoodImages(List<Commimages> list);

    List<String> LookGoodImages(String commid);

    void DelGoodImages(String commid);
}
