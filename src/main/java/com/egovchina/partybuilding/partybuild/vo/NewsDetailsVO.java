package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * desc: 党建资讯(详情)-视图对象
 * Created by FanYanGen on 2019-05-17 19:56
 */
@Data
@ApiModel("党建资讯(详情)-视图对象")
public class NewsDetailsVO {

    @ApiModelProperty(value = "资讯id")
    private Long newsId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "发布组织id")
    private String orgId;

    @ApiModelProperty(value = "发布组织名称")
    private String orgName;

    @ApiModelProperty(value = "发布组织简称")
    private String orgShortName;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishTime;

    @ApiModelProperty(value = "发布状态 1发布；0未发布；默认0")
    private Integer publishStatus;

    @ApiModelProperty(value = "操作人")
    private String operator;

    @ApiModelProperty(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date operatingTime;

    @ApiModelProperty(value = "操作人所属党组织名称")
    private String operatorPartyOrganizationName;

    @ApiModelProperty(value = "操作人所属党组织简称")
    private String operatorPartyOrganizationShortName;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "浏览数")
    private Long watchNum;

    @ApiModelProperty(value = "资讯内容")
    private String content;

    @ApiModelProperty(value = "排序码")
    private String orderNum;

    @ApiModelProperty(value = "接收方信息")
    private List<NewsReceiveVO> receives;

    @ApiModelProperty(value = "附件实体集合")
    private List<TabPbAttachment> attachments;

}
