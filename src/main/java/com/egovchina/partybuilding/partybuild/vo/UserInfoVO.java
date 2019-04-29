package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("当前操作用户信息实体")
@Data
public class UserInfoVO {
    @ApiModelProperty(value = "后台用户ID，后台用户一定有值，党员不一定有值")
    private Long userId;
    /**
     * 后台用户没有ci此组织id，只有管理组织id
     */
    @ApiModelProperty(value = "组织ID ,党支部Id")
    private Long deptId;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "用户名1")
    private String realname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "用户名")
    private String username;
    /**
     * 后台用户保存管理组织id
     */
    @ApiModelProperty(value = "后台用户管理组织ID")
    private Integer manageDeptId;
}
