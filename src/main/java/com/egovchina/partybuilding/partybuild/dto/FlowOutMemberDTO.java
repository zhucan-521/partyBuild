package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "流出党员扩展类")
@Data
public class FlowOutMemberDTO {

    @ApiModelProperty(value = "流出党员信息ID")
    private Long flowOutId;

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "流动证号")
    private String flowToOrgnizeCode;

    @ApiModelProperty(value = "性别")
    private Long gender;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "原职业 码表ZY")
    private Long outProfession;

    @ApiModelProperty(value = "外出原因 码表LDYY")
    private Long flowOutReason;

    @ApiModelProperty(value = "流动类型 码表LDQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowOutType;

    @ApiModelProperty(value = "流出范围 码表WCHY")
    private Long outIndustry;

    @ApiModelProperty(value = "流出组织名称")
    @NotNull(message = "请选择流出组织名称")
    private String flowFromOrgName;

    @ApiModelProperty(value = "流出组织Id")
    private Long orgId;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "流出日期",example = "yyyy-MM-dd")
    private Date flowOutDate;

    @ApiModelProperty(value = "流出组织联系人")
    private String flowFromOrgContactor;

    @ApiModelProperty(value = "流出组织联系电话")
    private String flowFromOrgPhone;

    @ApiModelProperty(value = "流入组织名称")
    @NotNull(message = "请选择流入组织名称")
    private String flowToOrgName;

    @ApiModelProperty(value = "流入组织Id")
    private Long flowOutPlace;

    @ApiModelProperty(value = "流入地址")
    private String flowToUnitName;

    @ApiModelProperty(value = "流入党支部联系方式")
    private String flowToOrgPhone;

    @ApiModelProperty(value = "流入党支部联系人")
    private String flowToOrgContactor;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "失联时间",example = "yyyy-MM-dd")
    private Date lostTime;

    @ApiModelProperty(value = "联系情况  码表LXQK")
    private Long linkStatus;

    @ApiModelProperty(value = "状态 码表值LDGC")
    private Long flowOutState;

    @ApiModelProperty(value = "流入时间",example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date flowInDate;

}
