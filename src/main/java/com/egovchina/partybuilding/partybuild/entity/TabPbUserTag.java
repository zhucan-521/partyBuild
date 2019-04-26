package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("tab_pb_user_tag")
@ApiModel(value = "user标签实体")
public class TabPbUserTag {
    private Long usertagId;

    private Long userId;

    @ApiModelProperty(value = "用户标签字典Id 码表值 USERTAG")
    @JsonSerialize(using = DictSerializer.class)
    private Long tagType;

    @ApiModelProperty(value = "字典id")
    @TableField(exist = false)
    private Integer id;

    @ApiModelProperty(value = "数据值", example = "AGE", required = true)
    @TableField(exist = false)
    private String value;

    @ApiModelProperty(value = "标签名", example = "过滤条件—年龄", required = true)
    @TableField(exist = false)
    private String label;

    @ApiModelProperty(value = "字典类型")
    @TableField(exist = false)
    private String type;

    @ApiModelProperty(value = "描述")
    @TableField(exist = false)
    private String description;

    @ApiModelProperty(value = "排序（升序）")
    @TableField(exist = false)
    private BigDecimal sort;

    @ApiModelProperty(value = "备注信息")
    @TableField(exist = false)
    private String remarks;

    @ApiModelProperty(value = "字典父级id(root字典为1)")
    @TableField(exist = false)
    private Integer parentId;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Long createUserid;

    @JsonIgnore
    private String createUsername;

    @JsonIgnore
    private Date updateTime;

    @JsonIgnore
    private Long updateUserid;

    @JsonIgnore
    private String updateUsername;

}