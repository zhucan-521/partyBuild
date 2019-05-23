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

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "坐标")
    private String coordinate;

    @ApiModelProperty(value = "层级")
    private String grade;

    @ApiModelProperty(value = "服务时间")
    private String serviceHours;

}
