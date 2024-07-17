package com.sen.framework.domain;

import com.sen.common.core.domain.entity.User;
import com.sen.common.core.domain.model.LoginUser;
import com.sen.common.utils.SecurityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * @author SEN
 * @version 1.0.0
 * @date 2024-06-30
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // TODO 查询数据库判断用户是否存在，或者校验用户
        User user = new User();
        user.setUserId(10000L);
        user.setUserName("admin");
        user.setPassword(SecurityUtils.encryptPassword("123456"));
        user.setDeptId(100L);
        return new LoginUser(user.getUserId(), user.getDeptId(), user, new HashSet<>());
    }
}
