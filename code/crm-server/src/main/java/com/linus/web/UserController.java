package com.linus.web;

import com.github.pagehelper.PageInfo;
import com.linus.model.TUser;
import com.linus.query.UserQuery;
import com.linus.result.R;
import com.linus.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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

    //新增用戶
    @PostMapping("/api/user")
    public R addUser(UserQuery userQuery, @RequestHeader("Authorization")String token){
        userQuery.setToken(token);
        int save = userService.saveUser(userQuery);
        return save >= 1 ? R.OK() : R.FAIL();
    }

    //編輯用戶
    @PutMapping("/api/user")
    public R editUser(UserQuery userQuery, @RequestHeader("Authorization")String token){
        userQuery.setToken(token);
        int update = userService.updateUser(userQuery);
        return update >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping("/api/user/{id}")
    public R delUser(@PathVariable("id")Integer id){
        int del = userService.delUserById(id);
        return del >= 1 ? R.OK() : R.FAIL();
    }

    @DeleteMapping("/api/user")
    public R batchDelUser(@RequestParam("ids")String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        int batchDel = userService.batchDelUserByIds(idList);
        return batchDel >= idList.size() ? R.OK() : R.FAIL();
    }

    @GetMapping("/api/owner")
    public R owner(){
        List<TUser> ownerList = userService.getOwnerList();
        return R.OK(ownerList);
    }

}
