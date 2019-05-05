package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel(value = "党员详情返回实体")
@Data
public class PartyMemberVO {

    @ApiModelProperty(value = "人员信息")
    private SysUserVO party;

    @ApiModelProperty(value = "学历信息")
    private List<PartyEducationVO> educations;

    @ApiModelProperty(value = "技术职务")
    private List<PartyJobTitleVO> jobTitles;

    @ApiModelProperty(value = "工作信息")
    private List<PartyWorkVO> works;

}
