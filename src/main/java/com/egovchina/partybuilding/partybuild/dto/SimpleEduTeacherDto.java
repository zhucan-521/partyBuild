package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduCourse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 简单版教育师资dto
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "简单版教育师资dto 智能选学使用")
@Data
public class SimpleEduTeacherDto {

    @ApiModelProperty(value = "老师ID")
    private Long teacherId;

    @ApiModelProperty(value = "老师姓名")
    private String name;

    @ApiModelProperty(value = "课程信息")
    private List<TabPbEduCourse> courses;
}
