package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 组织信息dto实体
 *
 * @Author Zhang Fan
 **/
@Data
@ApiModel(value = "组织信息dto实体")
public class SysDeptDto extends SysDept {

    @ApiModelProperty(value = "单位信息实体")
    private List<TabPbUnitInfo> tabPbUnitInfos;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "父组织名称")
    private String parentName;
}
