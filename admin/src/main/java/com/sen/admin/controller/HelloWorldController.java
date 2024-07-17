package com.sen.admin.controller;

import com.sen.common.core.domain.ResponseData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-07-01
 */
@RestController
@RequestMapping("/user")
public class HelloWorldController {

    @PostMapping("/hello")
    public ResponseData hello() {
        HashMap<Object, Object> map = new HashMap<>();
        map.put("test", "测试");
        return ResponseData.success("请求成功", map);
    }

}
