package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class TabPbGrantCommitteMember {
    @ApiModelProperty(hidden = true)
    private static final String FORMAT = "yyyy-MM-dd";

    @ApiModelProperty(value = "班子成员Id")
    private Long grantCommitteeMemberId;

    @ApiModelProperty(value = "大工委主键")
    private Long grantCommitteeId;

    @ApiModelProperty(value = "人员Id", required = true)
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
    @JsonFormat(pattern = FORMAT, timezone = "GMT+8")
    private Date tenureBegin;

    @ApiModelProperty(value = "任期年限")
    private Long tenureDuration;

    @ApiModelProperty(value = "离任时间")
    @JsonFormat(pattern = FORMAT, timezone = "GMT+8")
    private Date tenureLeave;

    @ApiModelProperty(value = "是否兼任村委会/社区居委会委员 0 or 1", example = "1")
    private Byte asCommitteeMember;

    @ApiModelProperty(value = "是否兼任村委会/社区居委会主任, 0 or 1", example = "0")
    private Byte asCommitteeDirector;

    @ApiModelProperty(value = "班子职务排序")
    private Long leadMemberOrder;

    @ApiModelProperty(value = "有效标记", hidden = true)
    @JsonIgnore
    private String eblFlag;

    @ApiModelProperty(value = "删除标记", hidden = true)
    @JsonIgnore
    private String delFlag;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述", hidden = true)
    @JsonIgnore
    private String description;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id", hidden = true)
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名", hidden = true)
    @JsonIgnore
    private String createUsername;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @JsonIgnore
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id", hidden = true)
    @JsonIgnore
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名", hidden = true)
    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value = "版本", hidden = true)
    @JsonIgnore
    private Long version;

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
}