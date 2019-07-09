package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Jiang An
 **/
@Data
@ApiModel("活动VO")
public class ActivityVO {

    @ApiModelProperty("组织Id")
    private Long orgId;

    @ApiModelProperty("活动Id")
    private Long activitiesId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "缺席原因 查询数据字典QXYY 1为因病因事缺席 2为无故缺席")
    @JsonSerialize(using = DictSerializer.class)
    private Byte absentReason;

    @ApiModelProperty(value = "人数")
    private Long numberOfPeople;
}
