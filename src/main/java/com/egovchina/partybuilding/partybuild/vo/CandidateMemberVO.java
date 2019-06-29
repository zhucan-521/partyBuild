package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 活动待参加人信息
 * @author: HuFan
 * @date: 2019/6/26 23:08
 */
@ApiModel(value = "活动待参加人信息")
@Data
public class CandidateMemberVO {

    @ApiModelProperty(value = "在职党员列表")
    private List<CommunityPersonnelVO> onWorks;

}
