package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @description: 活动Vo
 * @author: WuYunJie
 * @create: 2019-05-10 14:27
 **/
@ApiModel(value = "活动Vo")
@Data
public class ActivitiesVO {

    @ApiModelProperty("活动id")
    private Long activitiesId;

    @ApiModelProperty("活动类型")
    private Long activitiesType;

    @ApiModelProperty(value = "活动标题")
    private String subject;

    @ApiModelProperty(value = "活动开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "活动组织id")
    private Long orgId;

    @ApiModelProperty(value = "活动组织名称")
    private String orgName;

}
