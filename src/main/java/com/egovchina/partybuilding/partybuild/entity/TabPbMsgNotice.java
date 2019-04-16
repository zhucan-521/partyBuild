package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "下达文件通知")
public class TabPbMsgNotice extends Model<TabPbMsgNotice> implements Serializable{

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "发布党组织名称id")
    private Long deptId;

    @ApiModelProperty(value = "发布党组织名称")
    private String deptName;

    @ApiModelProperty(value = "通知类别-dict，普通通知、会议通知、文件通知")
    @JsonSerialize(using = DictSerializer.class)
    private Long noticeType;

    @ApiModelProperty(value = "通知标题")
    private String noticeTitle;

    @ApiModelProperty(value = "通知内容")
    private String noticeContent;

    @ApiModelProperty(value = "发布状态，0.未发布、1.已发布(发布后可以取消)")
    private String state;

    @ApiModelProperty(value = "发布日期", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishTime;

    @ApiModelProperty(value = "删除标记，默认0，删除为1")
    private String delFlag;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间", example = "yyyy-MM-dd")
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id")
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名")
    private String createUsername;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间", example = "yyyy-MM-dd")
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id")
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名")
    private String updateUsername;

    @TableField(exist = false)
    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

    @TableField(exist = false)
    @ApiModelProperty(value = "组织list")
    private List<TabPbMsgNoticeDept> noticeDeptList;

    @TableField(exist = false)
    @ApiModelProperty(value = "签收情况")
    private String signingSituation;

    @TableField(exist = false)
    @ApiModelProperty(value = "图片")
    private Long photo;

    @TableField(exist = false)
    @ApiModelProperty(value = "文档")
    private Long doc;

    @TableField(exist = false)
    @ApiModelProperty(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织")
    private Long orgRange;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "开始时间", example = "yyyy-MM-dd")
    private Date stateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "结束时间", example = "yyyy-MM-dd")
    private Date endTime;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}