package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@ApiModel("组织联点领导对象")
@TableName("tab_pb_link_leader")
public class TabPbLinkLeader {

    @ApiModelProperty(value = "组织联点领导联点主键")
    private Long linkLedaerId;

    @ApiModelProperty(value = "组织主键",required = true)
    private Long deptId;

    @ApiModelProperty(value = "人员Id",required = true)
    private Long userId;


    @ApiModelProperty(value = "联点开始时间",example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date linkStartDate;
    @ApiModelProperty(value = "联点结束时间",example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date linkFinishedDate;

    @ApiModelProperty(value = "有效标记")
    @JsonIgnore
    private String eblFlag;

    @ApiModelProperty(value = "删除标记")
    @JsonIgnore
    private String delFlag;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述")
    private String description;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id")
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名")
    @JsonIgnore
    private String createUsername;


    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id")
    @JsonIgnore
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名")
    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value = "版本")
    @JsonIgnore
    private Long version;

    @ApiModelProperty(value = "联点说明")
    private String comment;

}