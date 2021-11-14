package com.demo.common;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 结果集
 * </p>
 *
 * @author duguotao
 * @version 1.0.0
 * @since Created in 2021/11/11
 */
@Data
public class JsonResult<T> implements Serializable {

    private int code;
    private String msg;
    private T data;

    public static <T> JsonResult<T> OK() {
        return OK(null);
    }

    public static <T> JsonResult<T> OK(T data) {
        return result(200, null, data);
    }

    public static <T> JsonResult<T> Fail(String msg) {
        return Fail(4006, msg);
    }

    public static <T> JsonResult<T> Fail(int code, String msg) {
        return result(code, msg, null);
    }

    private static <T> JsonResult<T> result(int code, String msg, T data) {
        JsonResult<T> result = new JsonResult<T>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}