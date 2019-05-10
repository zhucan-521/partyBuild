package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 描述:
 * 联点信息dto
 *
 * @outhor wuyunjie
 * @create 2018-12-17 16:00
 */
@Data
@ApiModel("组织联点领导VO")
public class LinkLeaderVO {

    @ApiModelProperty(value = "组织联点领导联点主键")
    private Long linkLedaerId;

    @ApiModelProperty(value = "组织主键")
    private Long deptId;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty(value = "联点开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date linkStartDate;

    @ApiModelProperty(value = "联点结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date linkFinishedDate;

    @ApiModelProperty(value = "联点说明")
    private String comment;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "连接领导姓名")
    private String realName;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "活动集合")
    private List<ActivitiesVO> activities;
}
