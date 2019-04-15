package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;


@Data
@ApiModel("班子成员")
public class TabPbLeadTeamMember {

    @ApiModelProperty("班子成员id")
    private Long memberId;

    @ApiModelProperty(value = "党组织主键", required = true)
    private Long orgId;

    @ApiModelProperty(value = "领导班子id")
    private Long leadTeamId;

    @ApiModelProperty("职务级别 dict JB")
    @JsonSerialize(using = DictSerializer.class)
    private String rank;

    @ApiModelProperty(value = "人员Id", required = true)
    private Long userId;

    @ApiModelProperty("人员姓名")
    private String personName;

    @ApiModelProperty("党内职务主键")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveId;

    @ApiModelProperty("党内职务名称")
    private String positiveName;

    @ApiModelProperty("任职方式")
    private Long tenureMode;

    @ApiModelProperty(value = "任职时间 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date tenureBegin;

    @ApiModelProperty("任职年限")
    private Long tenureDuration;

    @ApiModelProperty(value = "离任时间 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date tenureLeave;

    @ApiModelProperty("批准文号")
    private String approvalNumber;

    @ApiModelProperty("是否兼任村委会委员")
    private Byte asCommitteeMember;

    @ApiModelProperty("是否兼任村委主任")
    private Byte asCommitteeDirector;

    @ApiModelProperty("班子职务排序")
    private Long leadMemberOrder;

    @ApiModelProperty("图片")
    private String pictures;

    @ApiModelProperty(value = "附件实体")
    private List<TabPbAttachment> tabPbAttachments;

    @ApiModelProperty(value = "数据描述")
    private String description;

    /**
     * 有效标记
     */
    @JsonIgnore
    private String eblFlag;

    /**
     * 删除标记
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 排序码
     */
    @JsonIgnore
    private Long orderNum;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 创建用户Id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改用户Id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    @JsonIgnore
    private String updateUsername;

    /**
     * 版本
     */
    @JsonIgnore
    private Long version;

}