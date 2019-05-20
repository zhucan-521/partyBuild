package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * desc: 新闻资讯-数据传输对象
 * Created by FanYanGen on 2019-05-11 16:48
 */
@Data
@ApiModel("新闻资讯-数据传输对象")
public class NewsDTO {

    @ApiModelProperty(value = "资讯id")
    private Long newsId;

    @ApiModelProperty(value = "标题", required = true)
    @NotNull(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "发布组织id", required = true)
    @NotNull(message = "发布组织不能为空")
    private Long orgId;

    @ApiModelProperty(value = "排序码", required = true)
    @NotNull(message = "排序码不能为空")
    private Integer orderNum;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "资讯内容", required = true)
    @NotNull(message = "资讯内容不能为空")
    private String content;

    @ApiModelProperty(value = "附件实体集合")
    private List<TabPbAttachment> attachments;

}
