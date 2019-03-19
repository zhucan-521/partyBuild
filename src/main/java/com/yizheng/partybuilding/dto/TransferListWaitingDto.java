package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织关系接转 - 待办工作 - 列表
 */
@Data
@ApiModel(value = "组织关系接转 - 待办工作 - 列表实体")
public class TransferListWaitingDto {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "审批表主键Id")
    private Long itemId;

    @ApiModelProperty(value = "主键ID")
    private Long transferId;

    @ApiModelProperty(value = "党员姓名")
    private String username;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "原党组织Id")
    private Long flowOutDeptId;

    @ApiModelProperty(value = "原党组织")
    private String flowOutDeptName;

    @ApiModelProperty(value = "目标党组织Id")
    private Long flowInDeptId;

    @ApiModelProperty(value = "目标党组织")
    private String flowInDeptName;

    @ApiModelProperty(value = "当前办理部门")
    private String currentDeptName;

    @ApiModelProperty(value = "目前转接耗时")
    private Integer totalTimeConsuming;

    @ApiModelProperty(value = "当前耗时")
    private Integer currentTimeConsuming;
}
