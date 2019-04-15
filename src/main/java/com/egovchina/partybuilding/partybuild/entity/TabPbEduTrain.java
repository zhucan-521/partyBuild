package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.partybuild.dto.EchartsDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@ApiModel(value = "重点培训")
@Data
@Accessors(chain = true)
public class TabPbEduTrain {

    @ApiModelProperty(value = "培训ID")
    private Long trainId;

    @ApiModelProperty(value = "年份")
    private String years;

    @ApiModelProperty(value = "培训对象-dict")
    @JsonSerialize(using = DictSerializer.class)
    private Long trainObj;

    @ApiModelProperty(value = "培训名称")
    private String name;

    @ApiModelProperty(value = "培训简介")
    private String description;

    @ApiModelProperty(value = "培训封面，直接保存文件服务id")
    private String cover;

    @ApiModelProperty(value = "教学视频地址(多个用逗号分割)")
    private String video;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "重点培训区域：字典PXQY")
    @JsonSerialize(using = DictSerializer.class)
    private Long region;

    @ApiModelProperty(value = "人员名单集合")
    private List<TabPbEduTrainObj> trainObjList;

    @ApiModelProperty(value = "课程安排集合")
    private List<TabPbEduTrainCourse> courses;

    @ApiModelProperty(value = "附件集合")
    private List<TabPbAttachment> attachments;

    @ApiModelProperty(value = "视频数")
    private Long toolNum;

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

    @ApiModelProperty(value = "数据")
    private List<EchartsDto>test;
}