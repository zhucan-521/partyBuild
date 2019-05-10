package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("奖励返回实体对象")
@Data
public class RewardsVO {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "rewards_id", type = IdType.AUTO)
    private Long rewardsId;

    @ApiModelProperty(value = "奖励日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date rewardsDate;

    @ApiModelProperty(value = "用户名")
    private String realName;

    @ApiModelProperty(value = "奖励机构")
    private String rewardsOrgnize;

    @ApiModelProperty(value = "奖励机构Id")
    private Long rewardsOrgnizeId;

    @ApiModelProperty(value = "userId")
    private Long userId;

    @ApiModelProperty(value = "奖励名称 dict DYJCJL")
    @JsonSerialize(using = DictSerializer.class)
    private String rewardsName;

    @ApiModelProperty(value = "备注：奖励说明")
    private String comment;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

    @ApiModelProperty(value = "图片数量")
    private Long imgTool;

    @ApiModelProperty(value = "文件数量")
    private Long fileTool;
}
