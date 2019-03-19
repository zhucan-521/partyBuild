package com.yizheng.partybuilding.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@ApiModel(value = "消息主体")
@Data
@Accessors(chain = true)
public class TabPbNotice implements Serializable{

    @ApiModelProperty(value = "消息主体Id")
    private Long noticeId;

    @ApiModelProperty(value = "业务模块：1.默认，2.组织，3.党员，4.基层活动，5.党员教育，6.党代表，7.两新工委")
    private Integer noticeType;

    @ApiModelProperty(value = "标题")
    private String noticeTitle;

    @ApiModelProperty(value = "副标题")
    private String noticeSubTitle;

    @ApiModelProperty(value = "状态-是否发布，为 ture 表示已发布，否则未发布")
    private Boolean noticeState;

    @ApiModelProperty(value = "封面图")
    private String coverId;

    @ApiModelProperty(value = "文章来源，默认为创建者所在部门名称")
    private String noticeSource;

    @ApiModelProperty(value = "发布时间")
    private Date publishTime;

    @ApiModelProperty(value = "浏览次数")
    private Long browseCount;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "删除标记，默认0，删除为1")
    private String delFlag;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id")
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名")
    private String createUsername;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id")
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名")
    private String updateUsername;

    @ApiModelProperty(value = "内容")
    private String noticeContent;

    @ApiModelProperty(value="视频资源附件id")
    private String videoId;

    @ApiModelProperty(value = "接收人")
    private List<TabPbNoticeReceive> tabPbNoticeReceives;
}