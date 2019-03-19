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

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@ApiModel(value = "远教站点实体")
@Data
public class TabPbEduSite {
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "区域 dict QY")
    @JsonSerialize(using = DictSerializer.class)
    private Long region;

    @ApiModelProperty(value = "站点名称")
    @NotBlank(message = "站点名称不能为空")
    private String siteName;

    @ApiModelProperty(value = "是否是基地 1是；0否")
    private Byte ifPosition;

    @ApiModelProperty(value = "站点类型 dict ZDLX")
    @JsonSerialize(using = DictSerializer.class)
    private Long siteType;

    @ApiModelProperty(value = "建站时间 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date siteTime;

    @ApiModelProperty(value = "上网方式 dict SWFS")
    @JsonSerialize(using = DictSerializer.class)
    private Long siteNetPlay;

    @ApiModelProperty(value = "站点位置")
    private String siteAddress;

    @ApiModelProperty(value = "站点坐标")
    private String siteGrid;

    @ApiModelProperty(value = "简介（备用）")
    private String introduction;

    @ApiModelProperty(value = "管理员姓名")
    private String manageName;

    @ApiModelProperty(value = "管理员头像")
    private String manageCover;

    @ApiModelProperty(value = "性别 dict XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long manageSex;

    @ApiModelProperty(value = "政治面貌")
    private String manageFace;

    @ApiModelProperty(value = "上岗时间 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date manageMountGuard;

    @ApiModelProperty(value = "文化程度-学历 dict WHCD")
    @JsonSerialize(using = DictSerializer.class)
    private Long manageEducation;

    @ApiModelProperty(value = "联系电话")
    private String managePhone;

    @ApiModelProperty(value = "出生日期", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date manageBirthDate;

    @ApiModelProperty(value = "年龄")
    private Integer manageAge;

    @ApiModelProperty(value = "人员类型 dict ZDRYLX")
    @JsonSerialize(using = DictSerializer.class)
    private Long manageType;

    @ApiModelProperty(value = "培训情况")
    private String manageTraining;

    @ApiModelProperty(value = "站点图片")
    private List<TabPbAttachment> attachments;

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

}