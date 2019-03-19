package com.yizheng.partybuilding.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author
 */
@Data
public class ClientUser implements Serializable {

    private static final long serialVersionUID = 6625804982409264002L;

    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * accessToken
     */
    private String accessToken;
    /**
     * 访问令牌有效期
     */
    private Date accessTokenValidity;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 用户权限列表
     */
    private Set<String> permissions;
    /**
     * 用户角色列表
     */
    private Set<String> roles;
    /**
     * 组织ID
     */
    private Integer deptId;

}