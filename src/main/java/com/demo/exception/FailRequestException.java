package com.demo.exception;

/**
 * <p>
 * 自定义异常处理器
 * </p>
 *
 * @author duguotao
 * @version 1.0.0
 * @since Created in 2021/11/11
 */
public class FailRequestException extends RuntimeException {

    public FailRequestException(String message) {
        super(message);
    }
}
