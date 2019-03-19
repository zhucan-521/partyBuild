package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Accessors(chain = true)
@Data
@ApiModel(value = "基地课程实体")
public class TabPbEduPositionCourse {
    @ApiModelProperty(value = "课程ID")
	private Long id;

    @ApiModelProperty(value = "基地id")
	private Long positionId;

    @ApiModelProperty(value = "课程名称")
	private String courseName;

    @ApiModelProperty(value = "时长 dict SC")
    @JsonSerialize(using = DictSerializer.class)
	private Long duration;

    @ApiModelProperty(value = "老师姓名")
	private String teacherName;

    @ApiModelProperty(value = "上课时间", example = "yyyy-MM-dd hh:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
	private Date courseTime;

    @ApiModelProperty(value = "项目简介")
    private String introduction;

    @ApiModelProperty(value = "项目封面(文件服务id)")
    private String cover;

    /**
     * 有效标记
     */
    @JsonIgnore
    private String eblFlag;

    /**
     * 删除标记
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 排序码
     */
    @JsonIgnore
    private Long orderNum;

    /**
     * 创建时间
     */
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

    /**
     * 版本
     */
    @JsonIgnore
    private Long version;
}