package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@ApiModel(value = "党群VO")
@Data
public class PartyMassesVO {

    @ApiModelProperty(value = "党群id")
    private Long partyMassesId;

    @ApiModelProperty(value = "党群名称")
    private String partyMassesName;

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "坐标")
    private String coordinate;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "层级")
    private String grade;

    @ApiModelProperty(value = "服务时间")
    private String serviceHours;

    @ApiModelProperty(value = "简介")
    private String content;

}