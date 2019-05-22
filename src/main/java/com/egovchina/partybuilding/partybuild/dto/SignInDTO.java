package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 党群签到DTO
 *
 * @author WuYunJie
 **/
@ApiModel("党群签到DTO")
@Data
public class SignInDTO {

    @ApiModelProperty("党群活动id")
    @NotNull(message = "党群活动id不能为空")
    private Long partyMassesActivityId;

    @ApiModelProperty("参与人id列表")
    private List<Long> participantIds;
}
