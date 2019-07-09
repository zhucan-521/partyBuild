package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author create by GuanYingxin on 2019/5/22 19:57
 * @description 班子换届班子成员列表VO
 */
@ApiModel("班子换届班子成员列表VO")
@Data
@Accessors(chain = true)
public class LeadTeamMemberListVO {

    @ApiModelProperty("班子成员id")
    private Long memberId;

    @ApiModelProperty(value = "党组织主键")
    private Long orgId;

    @ApiModelProperty(value = "领导班子id")
    private Long leadTeamId;

    @ApiModelProperty("职务级别 dict JB")
    @JsonSerialize(using = DictSerializer.class)
    private Long rank;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty("人员名称")
    private String personName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("头像")
    private String avatar2;

    @ApiModelProperty("党内职务主键")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveId;

    @ApiModelProperty("党内职务名称")
    private String positiveName;

    @ApiModelProperty(value = "任职时间 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tenureBegin;

    @ApiModelProperty("入党日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinDate;
}
