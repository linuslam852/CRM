package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TUser;
import com.linus.result.R;
import com.linus.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/api/login/info")
    public R loginInfo(Authentication authentication){
        TUser tUser = (TUser) authentication.getPrincipal();
        return R.OK(tUser);
    }

    @GetMapping("/api/login/free")
    public R freeLogin(){
        return R.OK();
    }

    @GetMapping("/api/users")
    public R userPage(@RequestParam(value = "current", required = false) Integer current){
        if(current == null){
            current = 1;
        }
        PageInfo<TUser> pageInfo = userService.getUserByPage(current);

        return R.OK(pageInfo);
    }

    @GetMapping("/api/user/{id}")
    public R userDetail(@PathVariable(value = "id")Integer id){
        TUser tUser = userService.getUserById(id);
        return R.OK(tUser);
    }

}
