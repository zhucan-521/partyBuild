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
@ApiModel(value = "师资管理")
public class TabPbEduTeachers extends Model<TabPbEduTeachers> implements Serializable {
    @ApiModelProperty(value = "主键ID")
    private Long teacherId;

    @ApiModelProperty(value = "账号",required = true)
    private String account;

    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "民族-MZ-dict")
    private Long nation;

    @ApiModelProperty(value = "老师姓名",required = true)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value = "出生日期",required = true)
    private Date birthTime;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "性别-XB-dict")
    private Long gender;

    @ApiModelProperty(value = "证件号")
    private String idCard;

    @ApiModelProperty(value = "政治面貌")
    private String politicalStatus;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "区域-QY-dict",required = true)
    private Long region;

    @ApiModelProperty(value = "手机号码")
    private String tellPhone;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "健康情况")
    private String health;

    @ApiModelProperty(value = "工作单位")
    private String workAddress;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "擅长领域-SCLY-dict")
    private Long goodField;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "师资类型-SZLX-dict")
    private Long teacherType;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "个人简介")
    private String description;

    @ApiModelProperty(value = "删除标记",hidden = true)
    private String delFlag;

    @ApiModelProperty(value = "排序码",hidden = true)
    private Long orderNum;

    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id",hidden = true)
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名",hidden = true)
    private String createUsername;

    @ApiModelProperty(value = "修改时间",hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id",hidden = true)
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名",hidden = true)
    private String updateUsername;

    @TableField(exist = false)
    @ApiModelProperty(value = "保存附件和修改附件用")
    private List<TabPbAttachment> attachments;

    @TableField(exist = false)
    @ApiModelProperty(value = "查看附件信息用")
    private String attachmentInstance;

    @TableField(exist = false)
    @ApiModelProperty(value = "课程List")
    private List<TabPbEduTeachersCourse> courseList;


    @Override
    protected Serializable pkVal() {
        return null;
    }
}