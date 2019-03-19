package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@ApiModel(value = "授课师资实体")
@Data
public class TabPbEduSpecialTeacher {
    @ApiModelProperty(value = "id")
	private Long id;

    @ApiModelProperty(value = "专题id")
	private Long specialId;

    @ApiModelProperty(value = "老师名称")
	private String teacherName;

    @ApiModelProperty(value = "联系电话")
	private String teacherPhone;

    @ApiModelProperty(value = "职务")
	private String teacherPostived;

    @ApiModelProperty(value = "删除标识 0为正常  1为删除")
	private String delFlag;

    @ApiModelProperty(value = "排序码")
	private Long orderNum;

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