package com.egovchina.partybuilding.partybuild.v1.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author create by GuanYingxin on 2019/4/22 19:58
 * @description
 */
@ApiModel("根据人员类别和党籍处理获取党籍列表")
@Accessors(chain = true)
@Data
public class MembershipQueryBean {

    @ApiModelProperty(value = "人员类别 码表值 RYLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long identityType;

    @ApiModelProperty(value = "党籍处理 码表值 DJCL")
    @JsonSerialize(using = DictSerializer.class)
    private Long type;
}
