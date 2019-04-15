package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@ApiModel(value = "处分")
@Data
@TableName("tab_pb_punishment")
public class TabPbPunishment {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "punishment_id", type = IdType.AUTO)
    private Long punishmentId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "处分日期")
    private Date punishDate;

    @ApiModelProperty(value = "处分名称", required = true)
    @JsonSerialize(using = DictSerializer.class)
    private String punishName;

    @ApiModelProperty(value = "处分类型", required = true)
    private Long punishType;

    @ApiModelProperty(value = "是否移交司法机关：1 是;0 否(字符串只能有一个长度哟)", required = true,example = "0")
    private String judiciaryFlag;

    @ApiModelProperty(value = "处分机构", required = true)
    private String punishOrg;

    @ApiModelProperty(value = "处分机构Id", required = true)
    private Long punishOrgId;

    @ApiModelProperty(value = "userId", required = true)
    private Long userId;

    @ApiModelProperty(value = "批准机关级别 dict PZJGJB", required = true)
    @JsonSerialize(using = DictSerializer.class)
    private Long approvedLevel;

    @ApiModelProperty(value = "处分原因")
    private Long punishReason;

    @ApiModelProperty(value = "处分结果")
    private Long punishResult;

    @ApiModelProperty(value = "撤销日期")
    private Date revokeDate;

    @JsonIgnore
    @ApiModelProperty(value = "有效标记 1有效 0无效")
    private String eblFlag;

    @JsonIgnore
    @ApiModelProperty(value = "删除标志 0正常 1删除")
    private String delFlag;

    @ApiModelProperty(value = "排序号")
    private Long orderNum;

    @ApiModelProperty(value = "描述")
    private String description;

    @JsonIgnore
    private Date createTime;

    @JsonIgnore
    private Long createUserid;

    @JsonIgnore
    private String createUsername;

    @JsonIgnore
    private Date updateTime;

    @JsonIgnore
    private Long updateUserid;

    @JsonIgnore
    private String updateUsername;

    @JsonIgnore
    private Long version;

    @ApiModelProperty(value = "党员基本情况")
    private String baseStatus;

    @ApiModelProperty(value = "错误表现")
    private String mistake;

    @ApiModelProperty(value = "后续处理情况")
    private String followUp;

    @ApiModelProperty(value = "备注：处分说明")
    private String comment;

    @ApiModelProperty(value = "文档",example = "[\"1f8c1cff-fe95-03a9420620af.jpg\",\"1f8c1cff-fe95-03a9420620af.jpg\"]")
    private List<String> fileList;

    @ApiModelProperty(value = "图片",example = "[\"1f8c1cff-fe95-03a9420620af.jpg\",\"1f8c1cff-fe95-03a9420620af.jpg\"]")
    private List<String> imgList;

    private Long attachmentType;

    @ApiModelProperty(value = "图片数量（统计展示用，无法直接修改添加）")
    private Long imgTool;

    @ApiModelProperty(value = "文件数量（统计展示用，无法直接修改添加）")
    private Long fileTool;
}