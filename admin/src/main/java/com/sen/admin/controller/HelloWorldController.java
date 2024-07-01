package com.sen.admin.controller;

import com.sen.common.constant.Constants;
import com.sen.common.core.domain.ResponseData;
import com.sen.common.core.domain.model.vo.LoginBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-01
 */
@RestController
@RequestMapping("/user")
public class HelloWorldController {

    @PostMapping("/hello")
    public ResponseData hello(){
        ResponseData result = ResponseData.success("请求成功");

        return result;
    }

}
