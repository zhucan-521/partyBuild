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
@ApiModel(value = "党群配置清单VO")
@Data
public class PartyMassesConfigurationVO {

    @ApiModelProperty(value = "配置清单id")
    private Long partyMassesConfigurationId;

    @ApiModelProperty(value = "党群场地id")
    private Long partyMassesPlaceId;

    @ApiModelProperty(value = "类型 字典id")
    private Long configurationType;

    @ApiModelProperty(value = "数量")
    private Long quantity;

}
