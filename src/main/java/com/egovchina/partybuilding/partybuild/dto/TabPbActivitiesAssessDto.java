package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.partybuild.entity.TabPbActivitiesAssess;
import com.egovchina.partybuilding.partybuild.entity.TabPbActivitiesAssessVerify;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 民主评议dto实体
 *
 * @Author Zhang Fan
 **/
@Data
@ApiModel(value = "民主评议dto实体")
public class TabPbActivitiesAssessDto extends TabPbActivitiesAssess {

    @ApiModelProperty(value = "历史审核信息")
    private List<TabPbActivitiesAssessVerify> historyReviews;

    @ApiModelProperty(value = "评议正式党员")
    private List<AssessUserDto> formalUsers;

    @ApiModelProperty(value = "评议预备党员")
    private List<AssessUserDto> preUsers;

    @ApiModelProperty(value = "正式党员评议结果")
    private Long formalResult;

    @ApiModelProperty(value = "预备党员评议结果")
    private Long preResult;

    @ApiModelProperty(value = "审核结果 dict SHJG")
    @JsonSerialize(using = DictSerializer.class)
    private Long auditResult = 0L;

    @ApiModelProperty(value = "文档数")
    private Integer docNum;

    @ApiModelProperty(value = "图片数")
    private Integer imgNum;

    @ApiModelProperty(value = "视频数")
    private Integer videoNum;

}
