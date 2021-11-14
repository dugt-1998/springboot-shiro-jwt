package com.demo.controller;

import com.demo.common.JsonResult;
import com.demo.service.UserService;
import com.demo.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 测试一下
 * </p>
 *
 * @author duguotao
 * @version 1.0.0
 * @since Created in 2021/11/11
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    final UserService userService;
    final HttpServletRequest httpServletRequest;

    @GetMapping("/login")
    public JsonResult<String> login(String username, String password) {
        userService.login(username, password);
        return JsonResult.OK(JWTUtil.createToken(username));
    }

    // -------------- 权限注解 ----------------
    @GetMapping("/getM1")
    @RequiresPermissions(value = "perm:hello")
    public JsonResult<String> getM1() {
        return JsonResult.OK("hello word");
    }

    @GetMapping("/getM2")
    @RequiresPermissions(value = {"perm:hello", "perm:test"}, logical = Logical.OR)
    public JsonResult<String> getM2() {
        return JsonResult.OK("hello m2");
    }


    // -------------- 角色注解 ----------------
    @GetMapping("/getM3")
    @RequiresRoles(value = "admin")
    public JsonResult<String> getM3() {
        return JsonResult.OK("hello m3");
    }

    @GetMapping("/getM4")
    @RequiresRoles(value = {"admin", "emp"}, logical = Logical.OR)
    public JsonResult<String> getM4() {
        return JsonResult.OK("hello m4");
    }
}