package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author create by GuanYingxin on 2019/5/25 14:04
 * @description 将要到届的领导班子VO
 */
@ApiModel("将要到届的领导班子VO")
@Data
@Accessors(chain = true)
public class LeadTeamExpireVO {

    @ApiModelProperty("领导班子id")
    private Long leadTeamId;

    @ApiModelProperty("领导班子名称")
    private String leadTeamName;

}
