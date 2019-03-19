package com.yizheng.partybuilding.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "组织关系转接审批流程")
public class TabPbTransferItem {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    private Long itemId;

    @ApiModelProperty(value = "组织接转主键ID")
    private Long transferId;

    @ApiModelProperty(value = "审批顺序")
    private Integer shIndex;

    @ApiModelProperty(value = "操作时间")
    private Date optTime;

    @ApiModelProperty(value = "审核状态;数据字典58960")
    private Integer status;

    @ApiModelProperty(value = "操作结果")
    private String optResult;

    @ApiModelProperty(value = "党组织联系人")
    private String pbContacts;

    @ApiModelProperty(value = "党组织联系电话")
    private String pbPhone;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建人id")
    private Long createUserid;

    @ApiModelProperty(value = "上级审批时间")
    private Date updateTime;

    @ApiModelProperty(value = "审核党组织名称")
    private String deptName;

    @ApiModelProperty(value = "审核组织id")
    private Long deptId;

    @ApiModelProperty(value = "审核人id")
    private Long userId;

    @ApiModelProperty(value = "是否撤销状态,0否 ，1是")
    private String revokeFlag;

    @ApiModelProperty(value = "审批状态,0不能审批 ，1能审批，2已审批")
    private String isSpFlag;
}