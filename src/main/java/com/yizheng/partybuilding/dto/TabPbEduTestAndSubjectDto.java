package com.yizheng.partybuilding.dto;

import com.yizheng.partybuilding.entity.TabPbEduTest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 试卷与题目实体
 */
@ApiModel(value = "创建试卷实体")
@Data
public class TabPbEduTestAndSubjectDto {

    @ApiModelProperty("试卷实体")
    private TabPbEduTest test;

    @ApiModelProperty("试卷-题目集合")
    private List<TabPbSubjectForTestDto> subjects;
}
