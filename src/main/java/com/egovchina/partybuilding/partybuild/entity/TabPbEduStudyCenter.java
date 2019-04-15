package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@ApiModel(value = "学习中心实体")
@Data
public class TabPbEduStudyCenter {
    @ApiModelProperty(value = "数据ID")
    private Long studyId;

    @ApiModelProperty(value = "文件类型 dict STUDY_WJLX")
    @JsonSerialize(using = DictSerializer.class)
    private Long fileType;

    @ApiModelProperty(value = "学习类别 dict XXLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long studyType;

    @ApiModelProperty(value = "时长 dict SC")
    @JsonSerialize(using = DictSerializer.class)
    private Long duration;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "文件名、标题")
    @NotBlank(message = "文件名、标题不能为空")
    private String title;

    @ApiModelProperty(value = "点赞次数")
    private Long likeCount;

    @ApiModelProperty(value = "观看次数")
    private Long watchCount;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "文章来源")
    private String articlesSource;

    @ApiModelProperty(value = "是否重点推荐")
    private Byte isRecommend;

    @ApiModelProperty(value = "附件对象")
    private List<TabPbAttachment> attachments;

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
    @ApiModelProperty(value = "创建时间", example = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建用户Id
     */
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名")
    private String createUsername;

    @ApiModelProperty(value = "修改时间", example = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
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