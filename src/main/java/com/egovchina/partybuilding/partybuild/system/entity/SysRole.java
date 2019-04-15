package com.egovchina.partybuilding.partybuild.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "系统角色实体")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends Model<SysRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称 不能为空")
    private String roleName;

    @ApiModelProperty(value = "角色编码")
    @NotBlank(message = "角色标识 不能为空")
    private String roleCode;

    @ApiModelProperty(value = "角色描述")
    @NotBlank(message = "角色描述 不能为空")
    private String roleDesc;

    @ApiModelProperty(value = "创建用户Id")
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标识（0-正常,1-删除）")
    @TableLogic
    private String delFlag;

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }

}
