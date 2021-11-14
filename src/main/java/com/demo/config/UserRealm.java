package com.demo.config;

import com.demo.service.UserService;
import com.demo.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 自定义Realm, 实现Shiro安全认证
 * </p>
 *
 * @author duguotao
 * @version 1.0.0
 * @since Created in 2021/11/11
 */
@Component
@RequiredArgsConstructor
public class UserRealm extends AuthorizingRealm {

    final UserService userService;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        if (username == null || !JWTUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败或token已过期!");
        }

        userService.auth(username);
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = JWTUtil.getUsername(principals.toString());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 获得该用户角色
        String role = userService.getRole(username);
        // 用户拥有的权限
        List<String> permission = userService.getPermission(username);
        Set<String> roleSet = new HashSet<>();
        roleSet.add(role);
        Set<String> permissionSet = new HashSet<>(permission);
        //设置该用户拥有的角色和权限
        info.setRoles(roleSet);
        info.setStringPermissions(permissionSet);
        return info;
    }
}