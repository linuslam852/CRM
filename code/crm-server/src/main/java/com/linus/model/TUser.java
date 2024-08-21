package com.linus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;

/**
 * 用戶表
 * t_user
 */
@Data
public class TUser implements UserDetails, Serializable {

    private Integer id;

    private String loginAct;

    private String loginPwd;

    private String name;

    private String phone;

    private String email;

    /**
     * 帳號是否没有過期，0已過期 1正常
     */
    private Integer accountNoExpired;

    /**
     * 密碼是否没有過期，0已過期 1正常
     */
    private Integer credentialsNoExpired;

    /**
     * 賬戶是否沒有鎖定，0已鎖定 1正常
     */
    private Integer accountNoLocked;

    /**
     * 帳號是否啟用，0禁用 1啟用
     */
    private Integer accountEnabled;

    private Date createTime;

    private Integer createBy;

    private Date editTime;

    private Integer editBy;

    private Date lastLoginTime;

    //角色List
    private List<String> roleList;

    //權限標識符List
    private List<String> permissionList;

    private  List<TPermission> memuPermissionList;

    private TUser createByDo;
    private TUser editByDo;

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();

        if(!ObjectUtils.isEmpty(this.getRoleList())){
            this.getRoleList().forEach(role -> {
                list.add(new SimpleGrantedAuthority(role));
            });
        }

        if(!ObjectUtils.isEmpty(this.getPermissionList())){
            this.getPermissionList().forEach(permission -> {
                list.add(new SimpleGrantedAuthority(permission));
            });
        }


        return list;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.getLoginPwd();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.getLoginAct();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.getAccountNoExpired() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.getAccountNoLocked() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return this.getCredentialsNoExpired() == 1;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return this.getAccountEnabled() == 1;
    }
}