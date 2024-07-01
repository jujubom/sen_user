package com.sen.framework.config;

import com.sen.framework.config.auth.JwtAuthenticationFilter;
import com.sen.framework.config.auth.JwtAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * spring security配置
 *
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-29
 */
@Configuration
@EnableWebSecurity //开启Spring Security
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * token认证过滤器
     */
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //禁用csrf,由于使用的是jwt,我们这里不需要csrf
        http.cors().and().csrf().disable().authorizeRequests()
                //跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //web jars
                .antMatchers("/webjars/**").permitAll()
                //查看SQL监控（druid）
                .antMatchers("/druid/**").permitAll()
                //首页和登录页面
                .antMatchers("/").permitAll().antMatchers("/login").permitAll()
                //swagger
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/springfox-swagger-ui/**").permitAll()
                //验证码
                .antMatchers("/captcha.jpg**").permitAll()
                //服务监控
                .antMatchers("/actuator/**").permitAll()
                //其他所有请求需要身份验证
                .anyRequest().authenticated();
        //退出登录处理器
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        //token验证过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(userDetailsService, bCryptPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用自定义身份认证组件
        auth.authenticationProvider(jwtAuthenticationProvider());
    }

}
