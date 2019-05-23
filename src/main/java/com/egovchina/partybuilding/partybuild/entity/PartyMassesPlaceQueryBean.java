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
public class PartyMassesPlaceQueryBean {

    @ApiModelProperty(value = "党群id")
    private Long partyMassesId;

    @ApiModelProperty(value = "场地名称")
    private String placeName;

    @ApiModelProperty(value = "开放日")
    private String openDay;

    @ApiModelProperty(value = "开放时间段")
    private String openTimePeriod;

}
