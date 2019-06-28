package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/25 14:46:34
 */
@ApiModel(value = "查询参数")
@Data
public class AdministrativeDivisionQueryBean {

    @ApiModelProperty(value = "行政区划id")
    private Long administrativeDivisionId = 1L;//默认长沙市查所有下级

    @ApiModelProperty(value = "行政区划名称")
    private String administrativeDivisionName;

    @ApiModelProperty(value = "行政区划编码")
    private String administrativeDivisionCode;

    @ApiModelProperty(value = "上级行政区划id")
    private Long parentId;

    @ApiModelProperty(value = "层级 字典码值 XZQHJB")
    private Long level;

    @ApiModelProperty("组织范围 1 查当前行政区划及其直属行政区划； 2 查当前行政区划及所有下级行政区划；其他值 查本行政区划")
    private String administrativeDivisionRange = "2";//默认长沙市查所有下级

}
