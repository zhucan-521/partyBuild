package com.egovchina.partybuilding.partybuild.dto;

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
@ApiModel(value = "党群场地DTO")
@Data
public class PartyMassesPlaceDTO{

    @ApiModelProperty(value = "党群场地id")
    private Long partyMassesPlaceId;

    @ApiModelProperty(value = "党群id",required = true)
    @NotNull(message = "党群id不能为空")
    private Long partyMassesId;

    @ApiModelProperty(value = "场地名称",required = true)
    @NotNull(message = "场地名称不能为空")
    private String placeName;

    @ApiModelProperty(value = "场地设备")
    private String devices;

    @ApiModelProperty(value = "容纳人数")
    private Long capacity;

    @ApiModelProperty(value = "开放日")
    private String openDay;

    @ApiModelProperty(value = "开放时间段")
    private String openTimePeriod;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "电话")
    private String tel;

    @ApiModelProperty(value = "场地地址")
    private String address;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "服务内容")
    private String content;

}
