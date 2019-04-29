package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "历史党员查询实体")
@Data
public class HistoricalPartyMemberQueryBean {
    @ApiModelProperty("组织id")
    private Long deptId;

    @ApiModelProperty("用户名/姓名")
    private String realName;

    @ApiModelProperty("历史原因：出党3；停止党籍4；死亡5；其他6")
    private Long outType;

    @ApiModelProperty("列表范围 0 查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；不传查本级")
    private String orgRange;
}
