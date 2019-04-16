package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "师资课程管理")
public class TabPbEduTeachersCourse extends Model<TabPbEduTeachersCourse> {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "老师id",required = true)
    private Long teacherId;

    @ApiModelProperty(value = "授课标题",required = true)
    private String title;

    @ApiModelProperty(value = "课程封面")
    private String cover;

    @ApiModelProperty(hidden = true)
    private String delFlag;

    @ApiModelProperty(hidden = true)
    private Long orderNum;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private Long createUserid;

    @ApiModelProperty(hidden = true)
    private String createUsername;

    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @ApiModelProperty(hidden = true)
    private Long updateUserid;

    @ApiModelProperty(hidden = true)
    private String updateUsername;

    @ApiModelProperty(value = "授课提纲",required = true)
    private String outline;

    @TableField(exist = false)
    @ApiModelProperty(value = "附件：课件")
    private List<TabPbAttachment> attachments;


    @Override
    protected Serializable pkVal() {
        return null;
    }
}