package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织关系转接审批dto
 */
@Data
@ApiModel("组织关系转接审批实体")
public class TransferApprovalDto {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "审批主键ID")
    private Long itemId;

    @ApiModelProperty(value = "组织接转主键ID")
    private Long transferId;

    @ApiModelProperty(value = "审核状态;数据字典58960,58961未审批,58962审批通过,58963审批未通过")
    private Integer status;

    @ApiModelProperty(value = "操作结果")
    private String optResult;

}
