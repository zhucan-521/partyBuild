package com.egovchina.partybuilding.partybuild.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@ApiModel(value = "系统用户角色实体")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @TableId(type = IdType.INPUT)
    private Integer userId;

    @ApiModelProperty(value = "角色ID")
    @TableId(type = IdType.INPUT)
    private Integer roleId;

    @Override
    protected Serializable pkVal() {
        return this.userId;
    }
}
