package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 党员发展步骤-视图对象(用户检查党员信息是否存在)
 * Created by FanYanGen on 2019/4/23 15:32
 */
@Data
@ApiModel("党员发展步骤-视图对象")
public class CheckDevPartyMemberVO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("发展ID")
    private Long deptId;

    @ApiModelProperty("提示")
    private String message;

    @ApiModelProperty("结果")
    private Boolean result;

}
