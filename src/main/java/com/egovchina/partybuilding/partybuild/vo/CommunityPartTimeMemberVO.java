package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 社区兼职委员VO
 *
 * @author Zhang Fan
 **/
@ApiModel("社区兼职委员VO")
@Data
public class CommunityPartTimeMemberVO {

    @ApiModelProperty("委员id")
    private Long memberId;

    @ApiModelProperty(value = "党组织主键")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty("人员名称")
    private String personName;

    @ApiModelProperty("党内职务主键")
    @JsonSerialize(using = DictSerializer.class)
    private Long positiveId;

    @ApiModelProperty("职务级别 dict JB")
    @JsonSerialize(using = DictSerializer.class)
    private String rank;

    @ApiModelProperty(value = "性别")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "联系方式")
    private String phone;

}
