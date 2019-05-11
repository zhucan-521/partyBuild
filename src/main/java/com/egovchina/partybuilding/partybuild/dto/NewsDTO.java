package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * desc: 新闻资讯-数据传输对象
 * Created by FanYanGen on 2019-05-11 16:48
 */
@Data
@ApiModel("新闻资讯-数据传输对象")
public class NewsDTO {

    @ApiModelProperty(value = "资讯id", required = true)
    private Long newsId;

    @ApiModelProperty(value = "标题", required = true)
    @NotNull(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "资讯类型 dict NEWS", required = true)
    @NotNull(message = "资讯类型不能为空")
    private Long type;

    @ApiModelProperty(value = "发布组织id", required = true)
    @NotNull(message = "发布组织不能为空")
    private Long orgId;

    @ApiModelProperty(value = "发布时间", dataType = "Date", example = "2019-05-11", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "发布时间日期不能为空")
    private Date publishTime;

    @ApiModelProperty(value = "发布状态 1发布；0未发布；默认0", required = true)
    private Integer publishStatus;

    @ApiModelProperty(value = "置顶状态 1是；0否；默认0", required = true)
    private Integer topStatus;

    @ApiModelProperty(value = "排序码", required = true)
    @NotNull(message = "排序码不能为空")
    private Integer orderNum;

    @ApiModelProperty(value = "资讯内容", required = true)
    @NotNull(message = "资讯内容不能为空")
    private String content;

    @ApiModelProperty(value = "附件实体集合")
    private List<TabPbAttachment> attachments;

}
