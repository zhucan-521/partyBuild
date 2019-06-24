package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/25 14:46:34
 */
@ApiModel(value = "行政区划VO")
@Data
public class AdministrativeDivisionVO {

    @ApiModelProperty(value = "行政区划id")
    private Long administrativeDivisionId;

    @ApiModelProperty(value = "行政区划名称")
    private String administrativeDivisionName;

    @ApiModelProperty(value = "行政区划编码")
    private String administrativeDivisionCode;

    @ApiModelProperty(value = "上级行政区划id")
    private Long parentId;

    @ApiModelProperty(value = "层级 字典码值 XZQHJB")
    @JsonSerialize(using = DictSerializer.class)
    private Long level;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "党群id")
    private Long partyMassesId;

    @ApiModelProperty(value = "党群名称")
    private String partyMassesName;

}
