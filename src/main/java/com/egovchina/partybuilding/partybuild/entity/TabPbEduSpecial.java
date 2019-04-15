package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@ApiModel(value = "课程体系实体")
@Data
public class TabPbEduSpecial {

    @ApiModelProperty(value = "课程体系")
    private Long id;

    @ApiModelProperty(value = "区域 值 1 长沙市，2 区县，3 高新")
    private Long region;

    @ApiModelProperty(value = "课程专题")
    private String title;

    @ApiModelProperty(value = "课程类型-KCNB：党史国史、党务实务、基层治理、乡村振兴、其他")
    @JsonSerialize(using = DictSerializer.class)
    private Long specialType;

    @ApiModelProperty(value = "精品课程时长-KCSC，半天、一天、两天级以上")
    @JsonSerialize(using = DictSerializer.class)
    private Long duration;

    @ApiModelProperty(value = "课程负责人")
    private String courseManager;

    @ApiModelProperty(value = "职务")
    private String postived;

    @ApiModelProperty(value = "课程联络人")
    private String courseLiaisons;

    @ApiModelProperty(value = "联系方式")
    private String cellPhone;

    @ApiModelProperty(value = "接待人数-JDRL，20人以内、20-50人、50-100人、100人以上")
    @JsonSerialize(using = DictSerializer.class)
    private Long quantity;

    @ApiModelProperty(value = "专题时间", example = "yyyy-MM-dd hh:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone = "GMT+8")
    private Date specialTime;

    @ApiModelProperty(value = "项目封面(文件服务id)")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String introduction;

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