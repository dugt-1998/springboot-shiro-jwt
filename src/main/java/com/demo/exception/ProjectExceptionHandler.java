package com.demo.exception;

import com.demo.common.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 自定义异常处理器
 * </p>
 *
 * @author duguotao
 * @version 1.0.0
 * @since Created in 2021/11/11
 */
@RestControllerAdvice
@Slf4j
public class ProjectExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public JsonResult<String> exceptionHandler(HttpServletRequest request, Exception e) {
        if (e instanceof FailRequestException) {
            FailRequestException failRequestException = (FailRequestException) e;
            String message = failRequestException.getMessage();
            return JsonResult.Fail(4006, message);
        } else if (e instanceof ShiroException) {
            ShiroException shiroException = (ShiroException) e;
            return JsonResult.Fail(401, shiroException.getMessage());
        }

        log.error(e.getMessage(), e);
        HttpStatus status = getStatus(request);
        return JsonResult.Fail(status.value(), e.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}