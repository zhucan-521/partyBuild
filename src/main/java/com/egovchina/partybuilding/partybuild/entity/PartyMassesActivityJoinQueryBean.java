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
 * @date 2019/05/31 11:49:59
 */
@ApiModel(value = "查询参数")
@Data
public class PartyMassesActivityJoinQueryBean {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "党群活动ID")
    private Long partyMassesActivityId;

    @ApiModelProperty(value = "参加的行政区划ID")
    private Long administrativeDivisionId;

    @ApiModelProperty(value = "参加的行政区划名称")
    private String administrativeDivisionName;

}
