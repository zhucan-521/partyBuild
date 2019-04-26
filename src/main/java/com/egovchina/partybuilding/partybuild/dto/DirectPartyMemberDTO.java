package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 升格人员DTO
 *
 * @author wuyunjie
 * Date 2019-01-16 17:51
 */
@ApiModel("升格人员DTO")
@Data
public class DirectPartyMemberDTO {
    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "性别 dict XB")
    private Long gender;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "组织ID")
    private Long orgId;
}
