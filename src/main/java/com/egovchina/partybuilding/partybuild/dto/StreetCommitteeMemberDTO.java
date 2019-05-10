package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel("街道大工委人员DTO")
@Data
@Accessors(chain = true)
public class StreetCommitteeMemberDTO {

    @ApiModelProperty(value = "班子成员Id")
    private Long grantCommitteeMemberId;

    @ApiModelProperty(value = "大工委主键")
    private Long grantCommitteeId;

    @ApiModelProperty(value = "人员Id", required = true)
    @NotNull(message = "人员Id不能为空")
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
    private Long tenureMode;

    @ApiModelProperty(value = "任职时间", example = "2019-05-12")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tenureBegin;

    @ApiModelProperty(value = "任期年限")
    private Long tenureDuration;

    @ApiModelProperty(value = "离任时间", example = "2019-05-12")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tenureLeave;

    @ApiModelProperty(value = "是否兼任村委会/社区居委会委员 0 or 1", example = "1")
    private Byte asCommitteeMember;

    @ApiModelProperty(value = "是否兼任村委会/社区居委会主任, 0 or 1", example = "0")
    private Byte asCommitteeDirector;

    @ApiModelProperty(value = "班子职务排序")
    private Long leadMemberOrder;

    @ApiModelProperty(value = "当前工委组织Id", required = true)
    @NotNull(message = "当前工委组织Id不能为空")
    private Long committeeOrgId;

}