package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class FlowInMemberVO {

    @ApiModelProperty(value = "流入Id")
    private Long flowInId;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "姓名")
    private String username;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "流往组织名称")
    private String flowToOrgName;

    @ApiModelProperty(value = "原职业 码表ZY")
    @JsonSerialize(using = DictSerializer.class)
    private Long outProfession;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "流出日期", example = "yyyy-hh-dd")
    private Date flowOutDate;

    @ApiModelProperty(value = "流出组织联系人")
    private String flowFromOrgContactor;

    @ApiModelProperty(value = "流出组织联系电话")
    private String flowFromOrgPhone;

    @ApiModelProperty(value = "流入党支部联系方式")
    private String flowToOrgPhone;

    @ApiModelProperty(value = "流入党支部联系人")
    private String flowToOrgContactor;

    @ApiModelProperty(value = "组织主键")
    private Long orgId;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "流入时间", example = "yyyy-MM-dd")
    private Date flowInDate;

    @ApiModelProperty(value = "流入类别 码表值 LDQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowInType;

    @ApiModelProperty(value = "流动范围 码表LRFW")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowInRange;

    @ApiModelProperty(value = "流入原因 码表LDYY")
    @JsonSerialize(using = DictSerializer.class)
    private String flowInReason;

    @ApiModelProperty(value = "原所在地")
    private String oldPlace;

    @ApiModelProperty(value = "原单位名称")
    private String oldUnitName;

    @ApiModelProperty(value = "原组织")
    private Long oldOrgnizeId;

    @ApiModelProperty(value = "流动证号")
    private String oldOrgnizeCode;

    @ApiModelProperty(value = "原组织名称")
    private String oldOrgnizeName;

    @ApiModelProperty(value = "原党组织电话")
    private String oldOrgnizePhone;

    @ApiModelProperty(value = "原党组织联系人及联系方式 用 , 隔开")
    private String oldOrgnizeContactor;

    @ApiModelProperty(value = "原联系电话")
    private String oldContactPhone;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "回归时间", example = "yyyy-hh-dd")
    private Date returnDate;

    @ApiModelProperty(value = "对应流出id")
    private Long flowOutId;

    @ApiModelProperty(value = "流入状态 码表值LDGC")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowInState;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "失联时间", example = "yyyy-hh-dd")
    private Date lostTime;

    @ApiModelProperty(value = "失联情况 码表值LXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long linkStatus;

    @ApiModelProperty(value = "0流出日期大于当前日期，1流出日期小于等于当前日期")
    private Long isEnableAccept;

}
