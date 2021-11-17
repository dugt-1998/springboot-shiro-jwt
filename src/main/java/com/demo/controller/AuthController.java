package com.demo.controller;

import com.demo.common.JsonResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *
 * </p>
 *
 * @author duguotao
 * @version 1.0.0
 * @since Created in 2021/11/14
 */
@RestController
public class AuthController {

    @RequestMapping(path = "/unauthorized/{message}")
    public JsonResult<String> unauthorized(@PathVariable String message) {
        return JsonResult.Fail(message);
    }
}
