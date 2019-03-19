package com.yizheng.partybuilding.system.dto;

import com.yizheng.partybuilding.system.entity.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色Dto
 */
@ApiModel(value = "角色dto实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDTO extends SysRole {

	@ApiModelProperty(value = "角色组织ID")
	private Integer deptId;

	@ApiModelProperty(value = "组织名称")
	private String deptName;
}
