package com.yizheng.partybuilding.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "职务")
@Data
@TableName("tab_pb_positives")
public class TabPbPositives {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "positive_id", type = IdType.AUTO)
    private Integer positiveId;

    @ApiModelProperty(value = "用户id",required=true)
    private Long userId;

    @ApiModelProperty(value = "职务类型 1党内职务 2行政职务",required=true)
    private Integer positiveType;

    @ApiModelProperty(value = "任职 机构/党组织 主键",required=true)
    private Integer positiveOrgId;

    @ApiModelProperty(value = "任职 机构/党组织 名称",required=true)
    private String positiveOrg;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "职务名称，字典：党内职务（DNZW）；行政职务（HZZW）",required=true)
    private Long positiveName;

    @ApiModelProperty(value = "职务名称中文",required=true)
    private String positiveNameDict;

    @ApiModelProperty(value = "职务级别 ZWJB")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveLevel;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任职开始",required=true)
    private Date positiveStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任职结束",required=true)
    private Date positiveFinished;

    @JsonIgnore
    @ApiModelProperty(value = "有效标记 1有效 0无效")
    private String eblFlag;

    @JsonIgnore
    @ApiModelProperty(value = "删除标志 0正常 1删除")
    private String delFlag;

    @ApiModelProperty(value = "排序号")
    private Long orderNum;

    @ApiModelProperty(value = "描述")
    private String description;

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

    @JsonIgnore
    private Long version;

    @ApiModelProperty(value = "离职原因 dict LZYY")
    @JsonSerialize(using = DictSerializer.class)
    private Long leftReason;

    @ApiModelProperty(value = "离职方式 dict LZFS")
    @JsonSerialize(using = DictSerializer.class)
    private Long leftType;

    @TableField(exist = false)
    @ApiModelProperty(value = "数据字典排序")
    private Long sort;

}