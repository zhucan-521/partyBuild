package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * desc: 党建资讯(详情)-视图对象
 * Created by FanYanGen on 2019-05-17 19:56
 */
@Data
@ApiModel("党建资讯(详情)-视图对象")
public class NewsDetailsVO {

    @ApiModelProperty("主键ID")
    private Long newsId;

    @ApiModelProperty("发布组织ID")
    private Long orgId;

    @ApiModelProperty(value = "发布组织名称")
    private String orgName;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("排序码")
    private Long orderNum;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "资讯内容")
    private String content;

    @ApiModelProperty(value = "接收方信息")
    private List<NewsReceiveVO> receives;

    @ApiModelProperty(value = "附件实体集合")
    private List<TabPbAttachment> attachments;

}
