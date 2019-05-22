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
@ApiModel(value = "党群场地VO")
@Data
public class PartyMassesPlaceVO {

    @ApiModelProperty(value = "党群场地id")
    private Long partyMassesPlaceId;

    @ApiModelProperty(value = "党群id")
    private Long partyMassesId;

    @ApiModelProperty(value = "场地名称")
    private String placeName;

    @ApiModelProperty(value = "容纳人数")
    private Long capacity;

    @ApiModelProperty(value = "开放日")
    private String openDay;

    @ApiModelProperty(value = "开放时间段")
    private String openTimePeriod;

    @ApiModelProperty(value = "场地地址")
    private String address;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "服务内容")
    private String content;

}
