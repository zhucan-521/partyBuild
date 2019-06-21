package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author create by GuanYingxin on 2019/6/18 17:34
 * @description 提醒党员参加活动VO
 */
@ApiModel(value = "提醒党员参加活动VO")
@Accessors(chain = true)
@Data
public class ParticipateInTheActivityVO {

    @ApiModelProperty("党员id")
    private Long userId;

    @ApiModelProperty("党员姓名")
    private String realname;

    @ApiModelProperty("标签")
    private String label;

}

