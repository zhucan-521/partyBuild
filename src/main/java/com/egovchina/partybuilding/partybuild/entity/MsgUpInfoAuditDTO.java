package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhucan
 */
@Data
public class MsgUpInfoAuditDTO {

    @ApiModelProperty(value = "数据ID" ,required = true)
    private Long id;

    @ApiModelProperty(value = "审核结果 码表SHJG" )
    private Long auditAssess;

    @ApiModelProperty(value = "审核说明" )
    private String auditComment;

}
