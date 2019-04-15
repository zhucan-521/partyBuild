package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "党员学历实体")
@Data
public class TabPbPartyEducation {
    @ApiModelProperty(value = "主键")
    private Long educationId;

    @JsonIgnore
    @ApiModelProperty(value = "党员id")
    private Long userId;

    @ApiModelProperty(value = "学历 dict XL")
    @JsonSerialize(using = DictSerializer.class)
    private Long level;

    @ApiModelProperty(value = "学位 dict ")
    @JsonSerialize(using = DictSerializer.class)
    private Long degree;

    @ApiModelProperty(value = "毕业院校")
    private String graduatedSchool;

    @ApiModelProperty(value = "专业")
    private String spec;

    /**
     * 删除标识 1删除；0未删除；默认0
     */
    @JsonIgnore
    private Boolean delFlag;

    /**
     * 创建时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 创建用户id
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建用户姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间 yyyy-MM-dd HH:mm:ss
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改用户id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    @JsonIgnore
    private String updateUsername;
}