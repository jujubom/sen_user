package com.sen.framework.login;

import com.sen.common.core.domain.model.LoginUser;
import com.sen.framework.domain.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-29
 */
@Slf4j
@Component
public class LoginService {

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    public String login(String username, String password, String code, String uuid) {

        // TODO 验证码开关

        // 用户验证
        Authentication authentication = null;
        try {
            // TODO 对密码解密, 解密后的明文密码

            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            e.printStackTrace();
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        // 生成token
        return tokenService.createToken(loginUser);
    }
}
