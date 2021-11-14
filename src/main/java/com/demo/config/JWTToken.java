package com.demo.config;
 
import org.apache.shiro.authc.AuthenticationToken;

/**
 * <p>
 * token扩展
 * </p>
 *
 * @author duguotao
 * @version 1.0.0
 * @since Created in 2021/11/11
 */
public class JWTToken implements AuthenticationToken {

    private final String token;
 
    public JWTToken(String token) {
        this.token = token;
    }
 
    @Override
    public Object getPrincipal() {
        return token;
    }
 
    @Override
    public Object getCredentials() {
        return token;
    }
}