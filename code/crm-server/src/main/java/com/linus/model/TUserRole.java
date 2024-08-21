package com.linus.model;

import java.io.Serializable;
import lombok.Data;

/**
 * 用戶角色關係表
 * t_user_role
 */
@Data
public class TUserRole implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer roleId;

    private static final long serialVersionUID = 1L;
}