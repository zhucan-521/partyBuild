package com.egovchina.partybuilding.partybuild.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@ApiModel(value = "菜单实体")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID")
    @NotNull(message = "菜单ID不能为空")
    @TableId(value = "menu_id", type = IdType.INPUT)
    private Integer menuId;

    @ApiModelProperty(value = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    @ApiModelProperty(value = "菜单权限标识")
    private String permission;

    @ApiModelProperty(value = "父菜单ID")
    private Integer parentId;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "VUE页面")
    private String component;

    @ApiModelProperty(value = "排序值")
    @NotNull(message = "排序值不能为空")
    private Integer sort;

    @ApiModelProperty(value = "菜单类型 （0菜单 1跳转 2按钮）")
    private String type;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "0--正常 1--删除")
    @TableLogic
    private String delFlag;

    @ApiModelProperty(value = "前端URL")
    private String path;

    @ApiModelProperty(value = "备注-扩展字段")
    private String remark;

    @Override
    protected Serializable pkVal() {
        return this.menuId;
    }

}
