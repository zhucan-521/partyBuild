package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "课程安排实体")
@Data
public class TabPbEduSpecialCourse {

    @ApiModelProperty(value = "课程id")
    private Long id;

    @ApiModelProperty(value = "专题id")
    private Long specialId;

    @ApiModelProperty(value = "排序码（第一天、第二天、第三天等等")
    private Long orderNum;

    @ApiModelProperty(value = "上课时间（上午、下午）", example = "yyyy-MM-dd hh:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date courseTime;

    @ApiModelProperty(value = "类别标签（开班式、专题教学等）")
    private String typeLable;

    @ApiModelProperty(value = "教学主题")
    private String title;

    @ApiModelProperty(value = "上课内容")
    private String content;

    @ApiModelProperty(value = "删除标识 0为正常  1为删除")
    private String delFlag;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date createTime;

    /**
     * 创建用户Id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改用户Id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    @JsonIgnore
    private String updateUsername;


}