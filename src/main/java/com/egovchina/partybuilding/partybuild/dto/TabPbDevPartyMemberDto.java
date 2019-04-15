package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/01
 */
@ApiModel("党员发展步骤dto")
@Data
@Accessors(chain = true)
public class TabPbDevPartyMemberDto {

    @ApiModelProperty(name = "发展步骤id", required = true)
    private Long dpId;

    @ApiModelProperty(name = "环节状态, 来源为数据字典", required = true)
    private Long status;
}
