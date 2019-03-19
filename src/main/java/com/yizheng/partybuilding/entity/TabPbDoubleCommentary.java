package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import com.yizheng.commons.domain.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@ApiModel(value = "双诉双评")
@Data
public class TabPbDoubleCommentary {
    @ApiModelProperty(value = "ID")
    private Long commentaryId;

    @ApiModelProperty(value = "组织主键", required = true)
    private Long orgId;

    @ApiModelProperty(value = "评述年份 yyyy", required = true, example = "yyyy-MM-dd")
    private Long planYear;

    @ApiModelProperty(value = "上报日期 yyyy-MM-dd", required = true, example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reportDate;

    @ApiModelProperty(value = "审核日期 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date checkDate;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "评述内容")
    private String commentaryContent;

    @ApiModelProperty(value = "审核情况 dict")
    @JsonSerialize(using = DictSerializer.class)
    private Long checkResult;

    @ApiModelProperty(value = "审核机构ID")
    private Long checkOrg;

    @ApiModelProperty(value = "审核机构名称")
    private String checkOrgName;

    @ApiModelProperty(value = "审核人ID")
    private Long checkUser;

    @ApiModelProperty(value = "审核人名称")
    private String checkUserName;

    @ApiModelProperty(value = "审核说明")
    private String checkDesc;

    @ApiModelProperty(value = "组织名称")
    private String orgName;


    //==================

    @ApiModelProperty(value = "文档数")
    private Integer docNum;

    @ApiModelProperty(value = "图片数")
    private Integer imgNum;

    @ApiModelProperty(value = "视频数")
    private Integer videoNum;

    @ApiModelProperty(value = "附件实体集合")
    private List<TabPbAttachment> tabPbAttachments;

    //==================

    /**
     * 有效标记
     */
    @JsonIgnore
    private String eblFlag;

    /**
     * 删除标记
     */
    @JsonIgnore
    private String delFlag;

    /**
     * 排序码
     */
    @JsonIgnore
    private Long orderNum;

    /**
     * 创建时间
     */
    @JsonIgnore
    private Date createTime;

    /**
     * 创建用户Id
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
     * 修改用户Id
     */
    @JsonIgnore
    private Long updateUserid;

    /**
     * 修改用户姓名
     */
    @JsonIgnore
    private String updateUsername;

    /**
     * 版本
     */
    @JsonIgnore
    private Long version;

}