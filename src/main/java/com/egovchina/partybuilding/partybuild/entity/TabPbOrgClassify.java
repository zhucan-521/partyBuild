package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "分类定等实体")
@Data
public class TabPbOrgClassify {
    @ApiModelProperty(value = "ID")
    private Long orgClassifyId;

    @ApiModelProperty(value = "组织ID", required = true)
    private Long deptId;

    @ApiModelProperty(value = "定等级别 dict FLDD", required = true)
    @JsonSerialize(using = DictSerializer.class)
    private Long orgLevel;

    @ApiModelProperty(value = "定等日期 yyyy-MM-dd", required = true, example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date levelDate;

    /**
     * 有效标记 1有效
     */
    @JsonIgnore
    private String eblFlag;

    /**
     * 删除标记 0未删除  1已删除
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 序值
     */
    @JsonIgnore
    private Long orderNum;

    @ApiModelProperty(value = "定等描述（标题）", required = true)
    private String description;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人ID
     */
    @JsonIgnore
    private Long createUserid;

    /**
     * 创建人姓名
     */
    @JsonIgnore
    private String createUsername;

    /**
     * 修改时间
     */
    @JsonIgnore
    private Date updateTime;

    /**
     * 修改人ID
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改人姓名
     */
    @JsonIgnore
    private String updateUsername;

    /**
     * 版本
     */
    @JsonIgnore
    private Long version;

    @ApiModelProperty(value = "组织名称")
    private String deptName;
}