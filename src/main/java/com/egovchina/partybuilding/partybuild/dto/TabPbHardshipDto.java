package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbHardship;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Jiang An
 **/
@ApiModel("困难党员Dto")
@Data
public class TabPbHardshipDto extends TabPbHardship {

    @ApiModelProperty(value = "列表范围，组织id")
    private Long orgRange;
    @ApiModelProperty(value = "范围组织ID")
    private Long rangeDeptId;

}
