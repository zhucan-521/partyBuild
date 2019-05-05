package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 领导班子成员VO
 *
 * @author Zhang Fan
 **/
@ApiModel("领导班子成员VO")
@Data
public class LeadTeamMemberVO {

    @ApiModelProperty("班子成员id")
    private Long memberId;

    @ApiModelProperty(value = "党组织主键")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "领导班子id")
    private Long leadTeamId;

    @ApiModelProperty("职务级别 dict JB")
    @JsonSerialize(using = DictSerializer.class)
    private String rank;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty("人员名称")
    private String personName;

    @ApiModelProperty("党内职务主键")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveId;

    @ApiModelProperty("党内职务名称")
    private String positiveName;

    @ApiModelProperty("任职方式")
    private Long tenureMode;

    @ApiModelProperty(value = "任职时间 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tenureBegin;

    @ApiModelProperty("任职年限")
    private Long tenureDuration;

    @ApiModelProperty(value = "离任时间 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tenureLeave;

    @ApiModelProperty("批准文号")
    private String approvalNumber;

    @ApiModelProperty("是否兼任村委会委员")
    private Byte asCommitteeMember;

    @ApiModelProperty("是否兼任村委主任")
    private Byte asCommitteeDirector;

    @ApiModelProperty("班子职务排序")
    private Long leadMemberOrder;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "头像")
    private String avatar;
}
