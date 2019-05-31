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
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@ApiModel(value = "党群活动DTO")
@Data
public class PartyMassesActivityDTO {

    @ApiModelProperty(value = "党群活动id")
    private Long partyMassesActivityId;

    @ApiModelProperty(value = "党群id",required = true)
    @NotNull(message = "党群id不能为空")
    private Long partyMassesId;

    @ApiModelProperty(value = "活动场地id")
    private Long placeId;

    @ApiModelProperty(value = "标题",required = true)
    @NotNull(message = "党群活动标题不能为空")
    private String subject;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finishedTime;

    @ApiModelProperty(value = "地点")
    private String address;

    @ApiModelProperty(value = "封面图")
    private String cover;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

}
