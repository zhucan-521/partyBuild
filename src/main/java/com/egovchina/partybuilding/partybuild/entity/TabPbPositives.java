package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    private Integer positiveId;
    private Long userId;
    private Integer positiveType;

    @ApiModelProperty(value = "(组织名称)任职 机构/党组织 主键",required=true)
    private Integer positiveOrgId;
    private String positiveOrg;


    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "（党内职务）职务名称，字典：党内职务（DNZW）；行政职务（HZZW）",required=true)
    private Long positiveName;
    private String positiveNameDict;

    @ApiModelProperty(value = "（职务级别）职务级别 ZWJB")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveLevel;
    private Date positiveStart;
    private Date positiveFinished;
    private String eblFlag;
    private String delFlag;
    private Long orderNum;

    @ApiModelProperty(value = "（党内职务说明）描述")
    private String description;
    private Date createTime;
    private Long createUserid;
    private String createUsername;
    private Date updateTime;
    private Long updateUserid;
    private String updateUsername;
    private Long version;
    private Long leftReason;
    private Long leftType;
    private Long sort;
}