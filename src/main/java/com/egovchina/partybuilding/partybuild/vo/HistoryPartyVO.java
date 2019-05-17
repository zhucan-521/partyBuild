package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "历史党员查询实体")
@Data
public class HistoryPartyVO {
    @ApiModelProperty(value = "申请人姓名")
    private String realname;

    @ApiModelProperty(value = "减少党员id")
    private Long memberReduceId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "组织ID ,党支部Id")
    private Long deptId;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @TableField(exist = false)
    @ApiModelProperty(value = "党龄")
    private Integer partyStanding;

    @ApiModelProperty(value = "减少方式")
    @JsonSerialize(using = DictSerializer.class)
    private Long outType;

    @ApiModelProperty(value = "原所在支部")
    private String inWhereDeptName;

    @ApiModelProperty(value = "离开党组织时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date reduceTime;

    @ApiModelProperty(value = "出党方式")
    @JsonSerialize(using = DictSerializer.class)
    private Long quitType;

    @ApiModelProperty(value = "党籍 码表值 DJZT")
    @JsonSerialize(using = DictSerializer.class)
    private Long registryStatus;
}
