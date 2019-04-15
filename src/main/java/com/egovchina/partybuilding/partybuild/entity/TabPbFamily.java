package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author YangYingXiang
 */
@ApiModel(value = "家庭成员对象",description = "家庭成员实体类")
@Data
public class TabPbFamily implements Serializable {

    @ApiModelProperty(value = "家庭成员主键ID",required = true)
    private Long relationId;

    @ApiModelProperty(value = "人员主键ID")
    private Long userId;

    @ApiModelProperty(value = "称谓 码表值CW")
    @JsonSerialize(using = DictSerializer.class)
    private Long relation;

    @ApiModelProperty(value = "家庭成员主键",hidden = true)
    private Long relationUserId;

    @ApiModelProperty(value = "启用",hidden = true)
    private String eblFlag;

    @ApiModelProperty(value = "禁用",hidden = true)
    private String delFlag;

    @ApiModelProperty(value = "排序代码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "创建时间",hidden = true)
    private Date createTime;

    @ApiModelProperty(value = "创建人员ID",hidden = true)
    private Long createUserid;

    @ApiModelProperty(value = "创建人员姓名",hidden = true)
    private String createUsername;

    @ApiModelProperty(value = "最后修改时间",hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "最后修改人ID",hidden = true)
    private Long updateUserid;

    @ApiModelProperty(value = "最后修改人姓名",hidden = true)
    private String updateUsername;

    @ApiModelProperty(value = "版本号",hidden = true)
    private Long version;

    @TableField(exist = false)
    @ApiModelProperty(value = "姓名")
    private String username;

    @TableField(exist = false)
    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @TableField(exist = false)
    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @TableField(exist = false)
    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @TableField(exist = false)
    @ApiModelProperty(value = "政治面貌 码表值 ZZMM")
    @JsonSerialize(using = DictSerializer.class)
    private Long policyFace;

    @TableField(exist = false)
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @TableField(exist = false)
    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "职务级别 码表值 HZZW")
    private Long positive;

    @TableField(exist = false)
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @TableField(exist = false)
    @ApiModelProperty(value = "是否出国 1否，2是")
    private String whetherAbroad;

    @TableField(exist = false)
    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = " 地区名称 码表值 CGCJ")
    private Long abroad;




}