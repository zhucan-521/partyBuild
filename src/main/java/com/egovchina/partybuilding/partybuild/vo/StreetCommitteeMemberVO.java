package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("街道大工委人员VO")
@Data
public class StreetCommitteeMemberVO {
    @ApiModelProperty(value = "班子成员Id")
    private Long grantCommitteeMemberId;

    @ApiModelProperty(value = "大工委主键")
    private Long grantCommitteeId;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty(value = "党组织主键")
    private Long orgId;

    @ApiModelProperty(value = "领导班子Id")
    private Long leadTeamId;

    @ApiModelProperty(value = "党员Id")
    private Long partyMemberId;

    @ApiModelProperty(value = "人员姓名")
    private String personName;

    @ApiModelProperty(value = "党内职务主键")
    private Long positiveId;

    @ApiModelProperty(value = "党内职务名称")
    private String positiveName;

    @ApiModelProperty(value = "任职方式, 字典RZFS")
    @JsonSerialize(using = DictSerializer.class)
    private Long tenureMode;

    @ApiModelProperty(value = "任职时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tenureBegin;

    @ApiModelProperty(value = "任期年限")
    private Long tenureDuration;

    @ApiModelProperty(value = "离任时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tenureLeave;

    @ApiModelProperty(value = "是否兼任村委会/社区居委会委员 0 or 1", example = "1")
    private Byte asCommitteeMember;

    @ApiModelProperty(value = "是否兼任村委会/社区居委会主任, 0 or 1", example = "0")
    private Byte asCommitteeDirector;

    @ApiModelProperty(value = "班子职务排序")
    private Long leadMemberOrder;

    // 补全字段
    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private String gender;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "单位id")
    private Long unitId;

    @ApiModelProperty(value = "单位名称")
    private String unitName;


    @ApiModelProperty(value = "组织当前大公委组织id")
    private Long currentOrgId;

    @ApiModelProperty(value = "当前大公委名称")
    private String currentOrgName;

    @ApiModelProperty(value = "当届标识")
    private Byte current;

}