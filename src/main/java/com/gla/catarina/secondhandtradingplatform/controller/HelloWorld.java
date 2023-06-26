package com.gla.catarina.secondhandtradingplatform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: SecondHandTradingPlatform
 * @ClassName HelloWorld
 * @description:
 * @author: alfred-chenzhonghao
 * @create: 2023-06-26 23:10
 * @Version 1.0
 **/
@Controller
@RequestMapping("/hello")
public class HelloWorld {

    @RequestMapping("/helloworld")
    public String HelloWorld(){
        return "HelloWorld";
    }
}
