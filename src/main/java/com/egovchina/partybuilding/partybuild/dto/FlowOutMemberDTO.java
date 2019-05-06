package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "流出党员扩展类")
@Data
public class FlowOutMemberDTO {

    @ApiModelProperty(value = "流出党员信息ID", required = true)
    @NotNull(message = "请传入flowOutId")
    private Long flowOutId;

    @ApiModelProperty(value = "流出地单位Id")
    private Long orgId;

    @ApiModelProperty(value = "流出地单位名称")
    private String flowToOrgnizeName;

    @ApiModelProperty(value = "流入地Id")
    private Long flowOutPlace;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "失联时间", example = "yyyy-MM-dd")
    private Date lostTime;

    @ApiModelProperty(value = "联系情况  码表LXQK")
    private Long linkStatus;

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "流出日期", example = "yyyy-MM-dd")
    private Date flowOutDate;

    @ApiModelProperty(value = "流动类型 码表LDQK")
    private Long flowOutType;

    @ApiModelProperty(value = "流动范围 码表LCFW")
    private Long flowOutRange;

    @ApiModelProperty(value = "外出流动行业 码表WCHY")
    private Long outIndustry;

    @ApiModelProperty(value = "流出理由 码表LDYY")
    private Long flowOutReason;

    @ApiModelProperty(value = "流入党支部联系方式")
    private String flowToOrgPhone;

    @ApiModelProperty(value = "流入党支部联系人")
    private String flowToOrgContactor;

    @ApiModelProperty(value = "流入单位名称")
    private String flowToUnitName;

    @ApiModelProperty(value = "流动证号")
    private String flowToOrgnizeCode;

    @ApiModelProperty(value = "流入组织")
    private Long flowToOrgnizeId;

    @ApiModelProperty(value = "联系方式")
    private String contactType;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "联系地址")
    private String contactAddress;

    @ApiModelProperty(value = "原职业 码表ZY")
    private Long outProfession;

    @ApiModelProperty(value = "状态")
    private Long flowOutState;

    @ApiModelProperty(value = "流入地名称")
    private String flowOutOrgName;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别")
    private Long gender;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "流出组织联系人")
    private String flowFromOrgContactor;

    @ApiModelProperty(value = "流出组织联系电话")
    private String flowFromOrgPhone;

    @ApiModelProperty(value = "流入时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date flowInDate;

    @ApiModelProperty(value = "流入组织名称")
    private String flowToOrgName;

    @ApiModelProperty(value = "流出组织名称")
    private String flowFromOrgName;


}
