package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbEduSpecial;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSpecialCourse;
import com.egovchina.partybuilding.partybuild.entity.TabPbEduSpecialTeacher;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 描述:
 * 精品课程TDO
 *
 * @author wuyunjie
 * Date 2019-01-11 9:29
 */
@ApiModel(value = "课程体系TDO")
@Data
public class TabPbEduSpecialDto extends TabPbEduSpecial {
    @ApiModelProperty(value = "课程安排集合")
    private List<TabPbEduSpecialCourse> tabPbEduSpecialCourses;

    @ApiModelProperty(value = "授课师资集合")
    private List<TabPbEduSpecialTeacher> tabPbEduSpecialTeachers;
}
