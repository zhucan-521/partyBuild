package com.yizheng.partybuilding.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import com.yizheng.partybuilding.entity.TabPbActivitiesAssess;
import com.yizheng.partybuilding.entity.TabPbActivitiesAssessVerify;
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
