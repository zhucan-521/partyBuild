package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: WuYunJie
 * @create: 2019-05-13 22:08
 **/
@ApiModel("组织标签集合DTO")
@Data
@Accessors(chain = true)
public class OrgTagDTO {

    @ApiModelProperty(value = "组织标签id", hidden = true)
    private Long orgTagId;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织id不能为空")
    private Long orgId;

    @ApiModelProperty(value = "组织标签字典Id 码表值 ORGTAG")
    private List<Long> orgTagTypes;

}
