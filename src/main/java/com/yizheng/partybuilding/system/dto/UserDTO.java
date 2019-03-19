package com.yizheng.partybuilding.system.dto;

import com.yizheng.partybuilding.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@ApiModel(value = "用户实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends SysUser {
    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    private List<Integer> roleIds;

    @ApiModelProperty(value = "新密码")
    private String newPassword;
}
