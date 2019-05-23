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
@ApiModel(value = "党群服务事项VO")
@Data
public class PartyMassesMatterVO {

    @ApiModelProperty(value = "服务事项id")
    private Long partyMassesMatterId;

    @ApiModelProperty(value = "党群id")
    private Long partyMassesId;

    @ApiModelProperty(value = "服务类型 字典id")
    private Long serviceType;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "服务对象 字典id")
    private Long crowd;

    @ApiModelProperty(value = "电话")
    private String tel;

}
