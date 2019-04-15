package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(value = "组织关系转接")
public class TabPbTransfer {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long transferId;

    @ApiModelProperty(value = "组织接转类型:数据字典59001（基层党委内部转接、省（区、市）、部门（系统）范围内转接、全国范围内转接）")
    private Long flowOutType;

    @ApiModelProperty(value = "号数")
    private String code;

    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "党员姓名")
    private String username;

    @ApiModelProperty(value = "性别;数字字典45095")
    private Long gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "党员联系电话")
    private String phone;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "民族;数据字典41363")
    private Long nation;

    @ApiModelProperty(value = "党员类型;预备党员0、正式党员1")
    private Long pbType;

    @ApiModelProperty(value = "转出方式;转到岳麓区内同一党委下属支部")
    private String flowOutMode;

    @ApiModelProperty(value = "转出支部Id")
    private Long flowOutDeptId;

    @ApiModelProperty(value = "转出支部名称")
    private String flowOutDeptName;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "转出日期,yyyy-MM-dd hh:mm:ss")
    private Date flowOutDate;

    @ApiModelProperty(value = "接受支部Id")
    private Long flowInDeptId;

    @ApiModelProperty(value = "接受支部名称")
    private String flowInDeptName;

    @ApiModelProperty(value = "接受情况")
    private String flowInSituation;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "创建日期,yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "创建人Id", hidden = true)
    private Long createUserId;

    @ApiModelProperty(value = "创建人姓名", hidden = true)
    private String createUsername;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "党费截止日期")
    private Date membershipTime;

    @ApiModelProperty(value = "有效期")
    private Integer termValidity;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "登记时间")
    private Date registerTime;

    @ApiModelProperty(value = "所在党委通讯地址")
    private String address;

    @ApiModelProperty(value = "联系电话")
    private String phone2;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "邮编")
    private String zipCode;

    @ApiModelProperty(value = "转接原因")
    private String transferReason;

    @ApiModelProperty(value = "是否接收,0否 ，1是")
    private String receiveFlag;

    @ApiModelProperty(value = "是否撤销,0否 ，1是")
    private String revokeFlag;
}