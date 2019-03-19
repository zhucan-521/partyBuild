package com.yizheng.partybuilding.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@ApiModel(value = "系统角色菜单实体")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_menu")
public class SysRoleMenu extends Model<SysRoleMenu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId(type = IdType.INPUT)
    private Integer roleId;

    @ApiModelProperty(value = "菜单ID")
    @TableId(type = IdType.INPUT)
    private Integer menuId;

    @Override
    protected Serializable pkVal() {
        return this.roleId;
    }
}
