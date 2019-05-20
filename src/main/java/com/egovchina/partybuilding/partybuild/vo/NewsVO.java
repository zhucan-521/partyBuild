package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * desc: 新闻资讯-视图对象
 * Created by FanYanGen on 2019-05-11 16:48
 */
@Data
@ApiModel("新闻资讯-视图对象")
public class NewsVO {

    @ApiModelProperty(value = "资讯id")
    private Long newsId;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "资讯类型")
    @JsonSerialize(using = DictSerializer.class)
    private Long type;

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

    @ApiModelProperty(value = "浏览数")
    private Long watchNum;

    @ApiModelProperty(value = "置顶状态 1是；0否；默认0")
    private Integer topStatus;

    @ApiModelProperty(value = "排序码")
    private Integer orderNum;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "资讯内容")
    private String content;

    @ApiModelProperty(value = "文档数")
    private Integer docNum;

    @ApiModelProperty(value = "图片数")
    private Integer imgNum;

    @ApiModelProperty(value = "视频数")
    private Integer videoNum;

    @ApiModelProperty(value = "附件实体集合")
    private List<TabPbAttachment> attachments;

}
