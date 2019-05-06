package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 党小组成员ID集合-数据传输对象
 * Created by FanYanGen on 2019/5/6 09:44
 */
@Data
@ApiModel("党小组成员ID集合-数据传输对象")
public class PartyGroupMemberIdsDTO {

    @ApiModelProperty(value = "党员ID", required = true)
    @NotNull(message = "党员ID不能为空")
    private Long memberId;

}
