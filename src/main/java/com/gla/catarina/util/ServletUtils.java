package com.gla.catarina.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

public class ServletUtils {


    public static HttpSession getSession() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest().getSession();
    }

    public static String getUserId() {
        HttpSession session = getSession();
        return (String) session.getAttribute("userid");
    }

    public static String getAdminId() {
        HttpSession session = getSession();
        return (String) session.getAttribute("admin");
    }
}
