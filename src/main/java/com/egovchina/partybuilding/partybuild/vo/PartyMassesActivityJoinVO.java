package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/31 11:49:59
 */
@ApiModel(value = "党群活动关联VO")
@Data
public class PartyMassesActivityJoinVO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "党群活动ID")
    private Long partyMassesActivityId;

    @ApiModelProperty(value = "参加的行政区划ID")
    private Long administrativeDivisionId;

    @ApiModelProperty(value = "参加的行政区划名称")
    private String administrativeDivisionName;

}
