package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "主题标签")
@Data
@Accessors(chain = true)
public class TabPbSubjectTag {
    @ApiModelProperty(value = "主键")
    private Long tagId;

    @ApiModelProperty(value = "标签名")
    private String name;

    @ApiModelProperty(value = "统计项标识 1是；0否；默认0")
    private Boolean statisticFlag;

    @ApiModelProperty(value = "有效标识 1有效；0无效；默认1")
    private Boolean eblFlag;

    @ApiModelProperty(value = "删除标识 1已删除；0未删除；默认0")
    private Boolean delFlag;

    @ApiModelProperty(value = "排序码 默认0")
    private Integer orderNum;

    @ApiModelProperty(value = "标签范围 dict ZZHD  多个用,号分隔 ")
    @JsonSerialize(using = DictSerializer.class)
    private String scope;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间 yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建用户id")
    private Long createUserid;

    @ApiModelProperty(value = "创建用户姓名")
    private String createUsername;

    @ApiModelProperty(value = "修改时间 yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "修改用户id")
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名")
    private String updateUsername;
}