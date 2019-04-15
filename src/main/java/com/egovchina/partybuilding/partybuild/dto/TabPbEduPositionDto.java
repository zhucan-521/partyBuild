package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduPosition;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduPositionCourse;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduPositionProject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 教育基地dto
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "教育基地dto")
@Data
public class TabPbEduPositionDto extends TabPbEduPosition {

    @ApiModelProperty(value = "教育特色项目信息")
    private List<TabPbEduPositionProject> eduPositionProjects;

    @ApiModelProperty(value = "特色课程信息")
    private List<TabPbEduPositionCourse> eduPositionCourses;
}
