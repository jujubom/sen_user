package com.sen.framework.config.auth;

import cn.hutool.core.text.StrFormatter;
import com.alibaba.fastjson.JSON;
import com.sen.common.constant.HttpStatus;
import com.sen.common.core.domain.ResponseData;
import com.sen.framework.utils.ServletUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类，返回
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-01
 */
@Component
public class AuthenticationFailureImpl implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg = StrFormatter.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        ServletUtils.renderString(response, JSON.toJSONString(ResponseData.error(code, msg)));

    }
}
