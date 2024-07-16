package com.linus.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.linus.constant.Constants;
import com.linus.mapper.TUserMapper;
import com.linus.model.TUser;
import com.linus.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private TUserMapper tUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TUser tUser = tUserMapper.selectByLoginAct(username);

        if(tUser == null){
            throw new UsernameNotFoundException("登入帳號不存在");
        }
        return tUser;
    }

    @Override
    public PageInfo<TUser> getUserByPage(Integer current) {
        PageHelper.startPage(current, Constants.PAGE_SIZE);
        List<TUser> list = tUserMapper.selectUserByPage();
        PageInfo<TUser> info = new PageInfo<>(list);
        return info;
    }

    @Override
    public TUser getUserById(Integer id) {
        return tUserMapper.selectDetailById(id);
    }
}
