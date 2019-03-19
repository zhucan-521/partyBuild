package com.yizheng.partybuilding.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "党员考试最高-平均-最低得分实体")
@Data
public class TabPbEduUserTestScoreDto {

    @ApiModelProperty(value = "考试平均得分")
    private float avgScore;

    @ApiModelProperty(value = "考试最高得分")
    private float maxScore;

    @ApiModelProperty(value = "考试最低得分")
    private float minScore;
}
