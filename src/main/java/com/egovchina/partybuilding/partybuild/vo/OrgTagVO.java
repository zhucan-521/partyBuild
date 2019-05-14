package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description:
 * @author: WuYunJie
 * @create: 2019-05-14 09:27
 **/
@Data
public class OrgTagVO {

    @ApiModelProperty(value = "组织标签id")
    private Long orgTagId;

    @ApiModelProperty(value = "字典值")
    private String value;

    @ApiModelProperty(value = "字典标签")
    private String label;

    @ApiModelProperty(value = "字典id")
    private Integer id;

}
