package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 社区兼职委员查询实体
 *
 * @author Zhang Fan
 **/
@ApiModel("社区兼职委员查询实体")
@Data
public class CommunityPartTimeMemberQueryBean {

    @ApiModelProperty("列表范围 1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；其他 本组织")
    private String orgRange;

    @ApiModelProperty(value = "组织id", required = true)
    @NotNull(message = "组织id不能为空")
    private Long rangeDeptId;

    @ApiModelProperty("姓名")
    private String personName;

    @ApiModelProperty("职务级别")
    private Long rank;
}
