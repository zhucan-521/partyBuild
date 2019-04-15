package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@ApiModel(value = "远教站点设备实体")
@Data
public class TabPbEduSiteDevice {
    @ApiModelProperty(value = "ID")
	private Long id;

    @ApiModelProperty(value = "站点ID")
	private Long siteId;

    @ApiModelProperty(value = "设备名称")
	private String deviceName;

    @ApiModelProperty(value = "存放地址")
	private String deviceAddress;

    @ApiModelProperty(value = "品牌型号")
	private String deviceModel;

    @ApiModelProperty(value = "配备时间 yyyy-MM-dd", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date deviceTime;

    @ApiModelProperty(value = "性能情况 dict XNQK")
    @JsonSerialize(using = DictSerializer.class)
	private Long deviceStatus;

    @ApiModelProperty(value = "设备图片集")
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