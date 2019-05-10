package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 描述:
 * 流入党员dto
 *
 * @outhor asd
 * @create 2018-11-28 13:03
 */
@ApiModel(value = "流入党员dto", description = "流入党员dto")
@Data
public class FlowInMemberDTO {

    @ApiModelProperty(value = "流入Id")
    @NotNull(message = "请传入flowInId")
    private Long flowInId;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别")
    private Long gender;

    @ApiModelProperty(value = "姓名")
    private String username;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "流往组织名称")
    private String flowToOrgName;

    @ApiModelProperty(value = "原职业 码表ZY")
    private Long outProfession;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "流出日期", example = "yyyy-MM-dd")
    private Date flowOutDate;

    @ApiModelProperty(value = "流出组织联系人")
    private String flowFromOrgContactor;

    @ApiModelProperty(value = "流出组织联系电话")
    private String flowFromOrgPhone;

    @ApiModelProperty(value = "流入党支部联系方式")
    private String flowToOrgPhone;

    @ApiModelProperty(value = "流入党支部联系人")
    private String flowToOrgContactor;

    @ApiModelProperty(value = "组织主键", required = true)
    private Long orgId;

    @ApiModelProperty(value = "人员Id", required = true)
    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "流入时间", example = "yyyy-MM-dd")
    private Date flowInDate;

    @ApiModelProperty(value = "流入类别 码表值 LDQK")
    private Long flowInType;

    @ApiModelProperty(value = "流动范围 码表LRFW")
    private Long flowInRange;

    @ApiModelProperty(value = "流入原因 码表LDYY")
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

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "回归时间", example = "yyyy-MM-dd")
    private Date returnDate;

    @ApiModelProperty(value = "对应流出id")
    private Long flowOutId;

    @ApiModelProperty(value = "流入状态 码表值LDGC")
    private Long flowInState;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "失联时间")
    private Date lostTime;

    @ApiModelProperty(value = "失联情况 码表值LXQK")
    private Long linkStatus;

}