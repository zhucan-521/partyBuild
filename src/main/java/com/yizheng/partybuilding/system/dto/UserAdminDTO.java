package com.yizheng.partybuilding.system.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel(value = "后台用户列表实体")
@Data
public class UserAdminDTO {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户名1")
    private String realname;

    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @ApiModelProperty(value = "0-正常，1-删除")
    private String delFlag;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "创建用户Id")
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "创建人姓名")
    private String createUsername;

    @ApiModelProperty(value = "组织ID")
    private Integer manageDeptId;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "角色名称集合")
    private List<String> roleNames;
}


