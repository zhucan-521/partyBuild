package com.yizheng.partybuilding.system.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author: huang
 * Date: 2019/1/4
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DictTree extends TreeNode{

    private static final long serialVersionUID = 1L;

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

    @TableField(exist = false)
    @ApiModelProperty(value = "是否叶子节点")
    private String isLeaf;
}
