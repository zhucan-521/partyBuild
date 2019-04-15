package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduCourse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(value = "课程表dto")
@Data
@Accessors(chain = true)
public class TabPbEduCourseDto extends TabPbEduCourse {


    @ApiModelProperty(value = "老师名字")
    private String teacherName;

    @ApiModelProperty(value = "观看次数")
    private int watchCount;

    @ApiModelProperty(value = "评价次数")
    private int contentCount;

    @ApiModelProperty(value = "点赞次数")
    private Long  likeCount;

}
