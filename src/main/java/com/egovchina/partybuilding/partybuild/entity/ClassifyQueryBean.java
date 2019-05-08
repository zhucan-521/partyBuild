package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 分类定等-查询条件
 * Created by FanYanGen on 2019/5/7 11:30
 */
@Data
@ApiModel("分类定等-查询条件")
public class ClassifyQueryBean {

    @ApiModelProperty(value = "组织范围 1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；其他值 查本组织")
    private String orgRange;

    @ApiModelProperty(value = "组织ID", required = true)
    private Long orgId;

    @ApiModelProperty(value = "定等级别 dict FLDD")
    String orgLevel;

}
