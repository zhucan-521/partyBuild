package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@ApiModel(value = "服务事项DTO")
@Data
public class PartyMassesMatterDTO{

    @ApiModelProperty(value = "服务事项id")
    private Long partyMassesMatterId;

    @ApiModelProperty(value = "党群id",required = true)
    @NotNull(message = "党群id不能为空")
    private Long partyMassesId;

    @ApiModelProperty(value = "服务类型 字典id")
    private Long serviceType;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "服务对象 字典id")
    private Long crowd;

    @ApiModelProperty(value = "电话")
    private String tel;

}
