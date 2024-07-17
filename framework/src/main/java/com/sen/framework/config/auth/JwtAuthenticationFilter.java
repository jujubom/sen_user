package com.sen.framework.config.auth;


import com.sen.common.constant.Constants;
import com.sen.common.core.domain.model.LoginUser;
import com.sen.framework.domain.TokenService;
import com.sen.common.utils.SecurityUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-29
 */
@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private Lock lock = new ReentrantLock();

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (Objects.nonNull(loginUser) && Objects.isNull(SecurityUtils.getAuthentication())) {
            tokenService.verifyToken(loginUser);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        boolean locked = false;
        try {
            locked = lock.tryLock(2, TimeUnit.MILLISECONDS);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            MDC.put(Constants.SPAN_ID, uuid);
            response.addHeader(Constants.SPAN_ID, uuid);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
        chain.doFilter(request, response);
    }
}
