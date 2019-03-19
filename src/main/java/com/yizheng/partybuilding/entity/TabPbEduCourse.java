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

@ApiModel(value = "课程实体")
@Data
@Accessors(chain = true)
public class TabPbEduCourse {
    @ApiModelProperty(value = "课程ID")
	private Long courseId;

    @ApiModelProperty(value = "课程类型 dict 码表KCNB")
    @JsonSerialize(using = DictSerializer.class)
	private Long type;

    @ApiModelProperty(value = "课程名称")
	private String name;

    @ApiModelProperty(value = "课程封面")
	private String cover;

    @ApiModelProperty(value = "是否有教学视频，0为否，1为是")
	private Byte isVideo;

    @ApiModelProperty(value = "人数限制 dict 码表JDRL")
    @JsonSerialize(using = DictSerializer.class)
	private Long quantity;

    @ApiModelProperty(value = "课程时间 yyyy-MM-dd hh:mm", example = "yyyy-MM-dd hh:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm", timezone = "GMT+8")
	private Date courseTime;

    @ApiModelProperty(value = "课程时长 dict 码表KCSC")
    @JsonSerialize(using = DictSerializer.class)
	private Long duration;

    @ApiModelProperty(value = "关联老师id")
	private Long teacherId;

    @ApiModelProperty(value = "关联老师头像")
    private String teacherAvator;

    @ApiModelProperty(value = "关联老师姓名")
    private String teacherName;

    @ApiModelProperty(value = "教学形式 码表JXXS")
    @JsonSerialize(using = DictSerializer.class)
	private Long courseForms;

    @ApiModelProperty(value = "教学地点")
	private String address;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "业务类别，码表YWLB drct 标识是基地的课程还是重点培训的课程，或者其他业务的课程")
    @JsonSerialize(using = DictSerializer.class)
    private Long  busType;

    @ApiModelProperty(value = "课程视频地址")
    private String videoAddress;


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


}