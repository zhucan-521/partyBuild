package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * desc: 党小组成员-数据传输对象
 * Created by FanYanGen on 2019/4/29 15:00
 */
@Data
@ApiModel("党小组成员-数据传输对象")
public class PartyGroupMemberDTO {

    @ApiModelProperty(value = "党组ID", required = true)
    @NotNull(message = "党组ID不能为空")
    private Long groupId;

    private List<PartyGroupMemberInfoDTO> members;

}
