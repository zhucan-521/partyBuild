package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * desc: 双述双评模块-查询条件
 * Created by FanYanGen on 2019/4/24 16:22
 */
@Data
@ApiModel("双述双评模块-查询条件")
public class CommentaryQueryBean {

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty("组织范围 1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；其他值 查本组织")
    private String orgRange;

    @ApiModelProperty(value = "所属年度 yyyy")
    private String planYear;

    @ApiModelProperty(value = "上报日期-开始 yyyy-MM-dd")
    private String reportStartDate;

    @ApiModelProperty(value = "上报日期-结束 yyyy-MM-dd")
    private String reportEndDate;

}
