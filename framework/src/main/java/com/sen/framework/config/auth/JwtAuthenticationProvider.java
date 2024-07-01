package com.sen.framework.config.auth;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * 自定义用户密码校验逻辑
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-29
 */
public class JwtAuthenticationProvider extends DaoAuthenticationProvider {

    @Resource
    private PasswordEncoder passwordEncoder;

    public JwtAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        setUserDetailsService(userDetailsService);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if(authentication.getCredentials() == null){
            logger.debug("Authentraction failed: no credentials provided");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetalisAuthenticationProvider.badCredentials","Bad credentials"));
        }
        String presentedPassword = authentication.getCredentials().toString();

        if (!this.passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
            this.logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }


//        if(! new PasswordEncoder(salt).matches(userDetails.getPassword(),presentedPassword)){
//            //覆写密码验证逻辑  matches:匹配两者
//            logger.debug("Authentication failed: password does not match");
//            throw new BadCredentialsException(messages.getMessage(
//                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
//                    "Bad credentials"
//            ));
//        }
    }
}
