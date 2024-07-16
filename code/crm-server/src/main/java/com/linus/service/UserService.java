package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    PageInfo<TUser> getUserByPage(Integer current);

    TUser getUserById(Integer id);
}
