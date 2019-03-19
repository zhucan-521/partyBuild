package com.yizheng.partybuilding.dto;

import com.yizheng.partybuilding.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 * 组织升格dto
 *
 * @author wuyunjie
 * Date 2019-01-24 16:52
 */
@ApiModel(value = "组织升格人员转移dto")
@Data
public class OrganizationUpgradeDto{
    @ApiModelProperty(value = "下级支部组织人员集合map")
    private Map<String,List<SysUser>>  userListMap;

    @ApiModelProperty(value = "组织主键", required = true)
    private Long deptId;
}
