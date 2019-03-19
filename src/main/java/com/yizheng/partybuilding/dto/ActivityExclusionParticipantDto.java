package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动排除参与者dto实体
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "活动排除参与者dto实体")
@Data
public class ActivityExclusionParticipantDto {

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "结对组织ID")
    private Long pairOrgId;

    @ApiModelProperty(value = "结对组织名称")
    private String pairOrgName;

    @ApiModelProperty(value = "年老体弱")
    private List<Personnel> nltr;

    @ApiModelProperty(value = "经常外出")
    private List<Personnel> jcwc;
}
