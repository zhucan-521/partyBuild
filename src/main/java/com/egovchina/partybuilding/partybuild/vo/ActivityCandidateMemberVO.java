package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Jiang An
 **/
@Data
@ApiModel("活动人员VO")
public class ActivityCandidateMemberVO {

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "直属党员列表")
    private List<GroupVO> groups;

    @ApiModelProperty(value = "在职党员列表")
    private List<PersonnelEntityVO> onWorks;

    @ApiModelProperty(value = "流动党员列表")
    private List<PersonnelEntityVO> onFlows;

    @ApiModelProperty(value = "主持人列表")
    private List<PersonnelEntityVO> moderators;

    @ApiModelProperty(value = "年老体弱")
    private List<PersonnelEntityVO> nltrs;

    @ApiModelProperty(value = "经常外出")
    private List<PersonnelEntityVO> jcwcs;

}
