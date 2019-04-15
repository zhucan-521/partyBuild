package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "")
@Data
@Accessors(chain = true)
public class TabPbEduComParticipate implements Serializable{

    @ApiModelProperty(value = "主键Id")
    private Long id;

    @ApiModelProperty(value = "学习交流id",required = true)
    private Long cId;

    @ApiModelProperty(value = "标题，参与人发表的标题",required = true)
    private String title;

    @ApiModelProperty(value = "内容，参与人发表的内容",required = true)
    private String content;

    @ApiModelProperty(value = "党员id",required = true)
    private Long userId;

    @ApiModelProperty(value = "党员name",required = true)
    private String username;

    @ApiModelProperty(value = "发表提交时间",required = true)
    private Date createTime;

    @ApiModelProperty(value = "审核状态，默认0未审核，1审核成功，2审核失败",required = true)
    private String state;

    @ApiModelProperty(value = "审核结果")
    private String shResult;

    @ApiModelProperty(value = "审核人")
    private Long shUserId;

    @ApiModelProperty(value = "审核时间")
    private Date shCreateTime;

    @ApiModelProperty(value = " 删除标记，默认0，删除为1 ",hidden = true)
    private String delFlag;

    @TableField(exist = false)
    @ApiModelProperty(value = "附件list")
    private List<TabPbAttachment> pbAttachmentList;

    @TableField(exist = false)
    @ApiModelProperty(value = "类型")
    private Long type;

}