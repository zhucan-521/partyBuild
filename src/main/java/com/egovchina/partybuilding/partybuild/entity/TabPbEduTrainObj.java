package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(value = "人员名单")
public class TabPbEduTrainObj{
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Long trainId;

    @JsonIgnore
    private Long userId;

    @ApiModelProperty(value = "姓名",required = true)
    private String userName;

    @ApiModelProperty(value = "身份证",required = true)
    private String idCard;

    @ApiModelProperty(value = "性别，一个长度")
    private String gender;

    @ApiModelProperty(value = "职务")
    private String positive;

    @ApiModelProperty(value = "学历-dict")
    @JsonSerialize(using = DictSerializer.class)
    private Long education;

    @ApiModelProperty(value = "是否班级管理员")
    private Boolean adminFlag;

    @ApiModelProperty(value = "是否已报到")
    private Boolean reportFlag;

    @ApiModelProperty(value = "年龄")
    private Long age;

    @ApiModelProperty(value = "电话")
    private String tellPhone;

    @JsonIgnore
    private Long orderNum;

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

}