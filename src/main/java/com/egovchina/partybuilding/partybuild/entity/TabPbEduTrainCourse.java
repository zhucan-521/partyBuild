package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
public class TabPbEduTrainCourse {
    private Long id;

    @ApiModelProperty(value = "课程ID")
    private Long trainId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程时间")
    private Date courseTime;

    @ApiModelProperty(value = "教学形式-dict")
    @JsonSerialize(using = DictSerializer.class)
    private Long courseForms;

    @ApiModelProperty(value = "老师名称")
    private String teacherName;

    @ApiModelProperty(value = "教学地点")
    private String address;

    @ApiModelProperty(value = "课程评分")
    private String levelAvg;

    private Long orderNum;
}