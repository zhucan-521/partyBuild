package com.yizheng.partybuilding.system.entity;

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
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Administrator
 */
@ApiModel(value = "系统数据字典实体")
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_dict")
public class SysDict extends Model<SysDict> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "数据值",example = "AGE",required=true)
    @NotBlank(message = "字典项数据值不能为空")
    private String value;

    @ApiModelProperty(value = "标签名",example = "过滤条件—年龄",required=true)
    @NotBlank(message = "字典项标签不能为空")
    private String label;

    @ApiModelProperty(value = "字典类型(普通字典为父级字典的value，root字典为自身的value)",example = "AGE",required=true)
    @NotBlank(message = "字典项数据类型不能为空")
    private String type;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "排序（升序）")
    private BigDecimal sort;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createTime;

    @DateTimeFormat
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注信息")
    private String remarks;

    @JsonIgnore
    @ApiModelProperty(value = "删除标记",example = "0",required=true,notes = "0:正常;1:删除")
    @TableLogic
    private String delFlag;

    @ApiModelProperty(value = "父级id(root字典为1)",example = "0",required=true)
    private Integer parentId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
