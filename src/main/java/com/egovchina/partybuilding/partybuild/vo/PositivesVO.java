package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "职务返回实体")
@Data
public class PositivesVO {

    @ApiModelProperty(value = "id")
    private Integer positiveId;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "职务类型 1党内职务 2行政职务")
    private Integer positiveType;

    @ApiModelProperty(value = "(组织名称)任职 机构/党组织 主键")
    private Integer positiveOrgId;

    @ApiModelProperty(value = "任职 机构/党组织 名称")
    private String positiveOrg;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "党内职务 dict DNZW")
    private Long positiveName;

    @ApiModelProperty(value = "职务级别 dict ZWJB")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveLevel;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任职开始")
    private Date positiveStart;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任职结束")
    private Date positiveFinished;

    @ApiModelProperty(value = "（党内职务说明）描述")
    private String description;

    @ApiModelProperty(value = "离职原因 dict LZYY")
    @JsonSerialize(using = DictSerializer.class)
    private Long leftReason;

    @ApiModelProperty(value = "离职方式 dict LZFS")
    @JsonSerialize(using = DictSerializer.class)
    private Long leftType;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;


}
