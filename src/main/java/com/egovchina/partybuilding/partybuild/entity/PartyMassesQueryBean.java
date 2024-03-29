package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@ApiModel(value = "查询参数")
@Data
public class PartyMassesQueryBean {

    @ApiModelProperty(value = "党群名称")
    private String partyMassesName;

    @ApiModelProperty(value = "行政区划id")
    private Long administrativeDivisionId;

    @ApiModelProperty(value = "行政区划名称")
    private String administrativeDivisionName;

    @ApiModelProperty(value = "坐标")
    private String coordinate;

    @ApiModelProperty(value = "服务时间")
    private String serviceHours;

    @ApiModelProperty(value = "层级 字典码值 XZQHJB")
    private Long level;

    @ApiModelProperty("组织范围 1 查当前党群及其直属党群； 2 查当前党群及所有下级党群；其他值 查本党群")
    private String partyMassesRange = "";

}
