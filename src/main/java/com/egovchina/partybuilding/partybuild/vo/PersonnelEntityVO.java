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
@ApiModel("活动人员实体")
public class PersonnelEntityVO {

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String realName;

    @ApiModelProperty(value = "类型 dict USERTAG")
    @JsonSerialize(using = DictSerializer.class)
    private Long realType;

    @ApiModelProperty(value = "党小组ID")
    private Long groupId;

    @ApiModelProperty(value = "党小组名称")
    private String groupName;

    @ApiModelProperty(value = "缺席原因 查询数据字典QXYY 1为因病因事缺席 2为无故缺席")
    @JsonSerialize(using = DictSerializer.class)
    private Byte absentReason;

    @ApiModelProperty(value = "活动参与人id")
    private Long participantId;

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "活动id")
    private Long activitiesId;
}
