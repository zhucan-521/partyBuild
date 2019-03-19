package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "审批记录查询实体")
public class TransferListSpDto {

    @ApiModelProperty(value = "主键ID")
    private Long itemId;

    @ApiModelProperty(value = "操作时间")
    private Date optTime;

    @ApiModelProperty(value = "审核状态;数据字典58960")
    private Integer status;

    @ApiModelProperty(value = "操作结果")
    private String optResult;

    @ApiModelProperty(value = "审核组织id")
    private Long deptId;

    @ApiModelProperty(value = "主键ID")
    private Long transferId;

    @ApiModelProperty(value = "组织接转类型:数据字典59001（基层党委内部转接、省（区、市）、部门（系统）范围内转接、全国范围内转接）")
    private Long flowOutType;

    @ApiModelProperty(value = "组织接转类型名称")
    private String flowOutTypeName;

    @ApiModelProperty(value = "党员姓名")
    private String username;

    @ApiModelProperty(value = "党员联系电话")
    private String phone;

    @ApiModelProperty(value = "性别;数字字典45095")
    private Long gender;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "转出支部名称")
    private String flowOutDeptName;

    @ApiModelProperty(value = "接受支部名称")
    private String flowInDeptName;
}
