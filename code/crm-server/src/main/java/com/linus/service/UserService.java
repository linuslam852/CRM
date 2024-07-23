package com.linus.service;

import com.github.pagehelper.PageInfo;
import com.linus.model.TUser;
import com.linus.query.UserQuery;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    PageInfo<TUser> getUserByPage(Integer current);

    TUser getUserById(Integer id);

    int saveUser(UserQuery userQuery);

    int updateUser(UserQuery userQuery);

    int delUserById(Integer id);

    int batchDelUserByIds(List<String> idList);

    List<TUser> getOwnerList();
}
