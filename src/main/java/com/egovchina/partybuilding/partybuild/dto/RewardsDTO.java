package com.egovchina.partybuilding.partybuild.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel(value = "奖励DTO")
@Data
public class RewardsDTO {

    @ApiModelProperty(value = "id")
    @TableId(value = "rewards_id", type = IdType.AUTO)
    private Long rewardsId;

    @ApiModelProperty(value = "奖励日期",example ="yyyy-MM-dd")
    @NotNull(message = "奖励日期不能为空!")
    private Date rewardsDate;

    @ApiModelProperty(value = "奖励机构", required = true)
    private String rewardsOrgnize;

    @ApiModelProperty(value = "奖励机构Id", required = true)
    private Long rewardsOrgnizeId;

    @ApiModelProperty(value = "userId", required = true)
    private Long userId;

    @ApiModelProperty(value = "有效标记 1有效 0无效")
    private String eblFlag;

    @ApiModelProperty(value = "奖励名称 dict DYJCJL", required = true)
    @NotNull(message = "奖励名称不能为空!")
    private String rewardsName;

    @ApiModelProperty(value = "备注：奖励说明")
    private String comment;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;
}
