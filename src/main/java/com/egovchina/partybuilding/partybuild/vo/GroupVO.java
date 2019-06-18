package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author Jiang An
 **/
@Data
@ApiModel("党小组实体VO")
public class GroupVO {

    @ApiModelProperty(value = "党小组名称")
    private String groupName;

    @ApiModelProperty(value = "党小组成员列表")
    private List<PersonnelEntityVO> members;
}
