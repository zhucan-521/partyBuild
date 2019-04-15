package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduPositionCourse;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduPositionProject;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 智能选学实体
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "智能选学实体")
@Data
public class TabPbEduIntelligentStudyDto {

    @ApiModelProperty(value = "基地ID")
    private Long id;

    @ApiModelProperty(value = "基地名称")
    private String name;

    @ApiModelProperty(value = "基地类型")
    @JsonSerialize(using = DictSerializer.class)
    private Long type;

    @ApiModelProperty(value = "基地属性")
    @JsonSerialize(using = DictSerializer.class)
    private Long property;

    @ApiModelProperty(value = "基地地址")
    private String address;

    @ApiModelProperty(value = "接待容量")
    @JsonSerialize(using = DictSerializer.class)
    private Long quantity;

    @ApiModelProperty(value = "总时长")
    private Long totalDuration;

    @ApiModelProperty(value = "师资")
    private List<SimpleEduTeacherDto> teachers;

    @ApiModelProperty(value = "教育特色项目信息")
    private List<TabPbEduPositionProject> eduPositionProjects;

    @ApiModelProperty(value = "特色课程信息")
    private List<TabPbEduPositionCourse> eduPositionCourses;

}
