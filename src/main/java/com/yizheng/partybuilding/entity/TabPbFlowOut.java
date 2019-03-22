package com.yizheng.partybuilding.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "党员信息流出表")
@TableName(value = "tab_pb_flow_out")
@Data
public class TabPbFlowOut {

    @ApiModelProperty(value = "流出党员信息ID")
    private Long flowOutId;

    @ApiModelProperty(value = "流出地单位Id")
    @NotNull(message = "请选择流出地单位！")
    private Long orgId;

    @ApiModelProperty(value = "流出地单位名称")
    private String flowToOrgnizeName;

    @ApiModelProperty(value = "流入地Id")
    @NotNull(message = "请选择流入党组织！")
    private Long flowOutPlace;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "失联时间")
    private Date lostTime;

    @ApiModelProperty(value = "联系情况  码表LXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long linkStatus;

    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "流出日期")
    private Date flowOutDate;

    @ApiModelProperty(value = "流动类型 码表LDQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowOutType;

    @ApiModelProperty(value = "流动范围 码表LCFW")
    private Long flowOutRange;

    @ApiModelProperty(value = "外出流动行业 码表WCHY")
    @JsonSerialize(using = DictSerializer.class)
    private Long outIndustry;

    @ApiModelProperty(value = "流出理由 码表LDYY")
    @JsonSerialize(using = DictSerializer.class)
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
    @JsonSerialize(using = DictSerializer.class)
    private Long outProfession;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "回归时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date returnDate;

    @ApiModelProperty(value = "回归标识", hidden = true)
    private Byte returnTag;

    @ApiModelProperty(hidden = true)
    private String eblFlag;

    @ApiModelProperty(hidden = true)
    private String delFlag;

    @ApiModelProperty(value = "排序码", hidden = true)
    private Long orderNum;

    @ApiModelProperty(hidden = true)
    private String description;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+800000")
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private Long createUserid;

    @ApiModelProperty(hidden = true)
    private String createUsername;

    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(hidden = true)
    private Long updateUserid;

    @ApiModelProperty(hidden = true)
    private String updateUsername;

    @ApiModelProperty(hidden = true)
    private Long version;

    @ApiModelProperty(value = "状态")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowOutState;


}