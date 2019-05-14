package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * desc: 党建资讯-查询条件
 * Created by FanYanGen on 2019-05-11 16:53
 */
@Data
@ApiModel("党建资讯-查询条件")
public class NewsQueryBean {

    @ApiModelProperty(value = "组织ID", required = true)
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "组织范围 1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；其他值 查本组织")
    private Long orgRange;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "发布时间 yyyy-MM-dd")
    private String publishTime;

}
