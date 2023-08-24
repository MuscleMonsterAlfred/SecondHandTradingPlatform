package com.gla.catarina.vo;

import com.gla.catarina.util.StatusCode;

public class ResultVo {
    private boolean flag;   //是否成功
    private Integer status; //状态码
    private String message; //返回信息
    private Object data;    //返回数据

    public ResultVo() {
        super();
    }

    public ResultVo(boolean flag, Integer status, String message, Object data) {
        this.flag = flag;
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResultVo(boolean flag, Integer status, String message) {
        this.flag = flag;
        this.status = status;
        this.message = message;
    }

    public static ResultVo ok() {
        return res(true, StatusCode.OK, "Success", null);
    }

    public static ResultVo error() {
        return res(false, StatusCode.ERROR, "Fail", null);
    }

    public static ResultVo ok(Object data) {
        return res(true, StatusCode.OK, null, data);
    }
    public static ResultVo okData(Object data) {
        return res(true, StatusCode.OK, null, data);
    }

    public static ResultVo ok(String message) {
        return res(true, StatusCode.OK, message, null);
    }

    public static ResultVo error(String message) {
        return res(false, StatusCode.ERROR, message, null);
    }

    public static ResultVo res(boolean flag, Integer status, String message, Object data) {
        ResultVo res = new ResultVo();
        res.setFlag(flag);
        res.setStatus(status);
        res.setMessage(message);
        res.setData(data);
        return res;
    }

    public Integer getStatus() {
        return status;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
