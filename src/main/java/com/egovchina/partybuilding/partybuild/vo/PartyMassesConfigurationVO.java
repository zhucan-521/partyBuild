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

    @ApiModelProperty(value = "党群id")
    private Long partyMassesId;

    @ApiModelProperty(value = "类型")
    private String configurationType;

    @ApiModelProperty(value = "数量")
    private Long quantity;

}
