package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "培训动态")
public class TabPbEduTrainDynamic {

    private Long dynamicId;

    @ApiModelProperty(value = "重点培训id",required = true)
    private Long trainId;

    private String trainName;

    private Long userId;

    private String username;

    @ApiModelProperty(value = "动态标题",required = true)
    private String title;

    @ApiModelProperty(value = "动态内容",required = true)
    private String content;

    @ApiModelProperty(value = "提交日期")
    private Date createTime;

    @ApiModelProperty(value = "审核状态，默认0未审核，1为审核通过,2为审核不通过")
    private String state;

    private Date verifyTime;

    private Long verifyUserid;

    private String createUsername;

    @ApiModelProperty(value = "附件集合")
    private List<TabPbAttachment> attachments;

    @ApiModelProperty(value = "附件数")
    private Long toolNum;

}