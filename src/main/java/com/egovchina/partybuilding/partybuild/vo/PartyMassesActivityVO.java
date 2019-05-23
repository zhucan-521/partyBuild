package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author WuYunJie
 * @date 2019/05/20 21:37:39
 */
@ApiModel(value = "党群活动VO")
@Data
public class PartyMassesActivityVO {

    @ApiModelProperty(value = "党群活动id")
    private Long partyMassesActivityId;

    @ApiModelProperty(value = "党群id")
    private Long partyMassesId;

    @ApiModelProperty(value = "活动场地id")
    private Long placeId;

    @ApiModelProperty(value = "标题")
    private String subject;

    @ApiModelProperty(value = "状态：1 报名中， 2 进行中， 3 精彩回顾， 4 未知")
    private Long status;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date finishedTime;

    @ApiModelProperty(value = "地点")
    private String address;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

}
