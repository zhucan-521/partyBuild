package com.yizheng.partybuilding.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import com.yizheng.commons.domain.TabPbAttachment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

@ApiModel(value = "教育基地")
@Data
public class TabPbEduPosition {
    @ApiModelProperty(value = "基地ID")
    private Long id;

    @ApiModelProperty(value = "基地名称")
    @NotBlank(message = "基地名称不能为空")
    private String name;

    @ApiModelProperty(value = "是否是站点 1是；0否")
    private Byte ifSite;

    @ApiModelProperty(value = "基地类型 dict JDLX")
    @JsonSerialize(using = DictSerializer.class)
    private String type;

    @ApiModelProperty(value = "基地附加类型 dict JDLX")
    @JsonSerialize(using = DictSerializer.class)
    private String subTypes;

    @ApiModelProperty(value = "基地属性 dict JDSX")
    @JsonSerialize(using = DictSerializer.class)
    private String property;

    @ApiModelProperty(value = "基地地址")
    private String address;

    @ApiModelProperty(value = "交通信息")
    private String trafficInfo;

    @ApiModelProperty(value = "坐标")
    private String grid;

    @ApiModelProperty(value = "简介")
    private String introduction;

    @ApiModelProperty(value = "接待容量 dict JDRL")
    @JsonSerialize(using = DictSerializer.class)
    private Long quantity;

    @ApiModelProperty(value = "开放时间")
    private String openTime;

    @ApiModelProperty(value = "区域 dict QY")
    @JsonSerialize(using = DictSerializer.class)
    private Long region;

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "课程、项目总时长")
    private Long totalDuration;

    @ApiModelProperty(value = "基地照片")
    private List<TabPbAttachment> tabPbAttachments;

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