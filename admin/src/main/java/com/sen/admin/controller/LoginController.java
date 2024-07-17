package com.sen.admin.controller;

import com.sen.common.constant.Constants;
import com.sen.common.core.domain.ResponseData;
import com.sen.common.core.domain.model.LoginBody;
import com.sen.framework.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-29
 */
@RestController
public class LoginController {


    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseData login(@RequestBody LoginBody loginBody){
        ResponseData result = ResponseData.success();
        try {
            String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
            result.put(Constants.TOKEN, token);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseData.error(e.getMessage());
        }
        return result;
    }
}
