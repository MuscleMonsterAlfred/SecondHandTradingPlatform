package com.gla.catarina.vo;

import lombok.Data;

@Data
public class R<T> {

    private Integer code;

    private String msg;

    private T data;

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 失败
     */
    public static final int FAIL = 500;

    public static <T> R<T> ok() {
        return res(SUCCESS, "", null);
    }

    public static <T> R<T> ok(T data) {
        return res(SUCCESS, "", data);
    }

    private static <T> R<T> res(int code, String msg, T data) {
        R<T> res = new R<>();
        res.setCode(code);
        res.setData(data);
        res.setMsg(msg);
        return res;
    }
}
