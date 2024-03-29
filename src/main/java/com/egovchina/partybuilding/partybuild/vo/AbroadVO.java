package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * desc: 出国出境-视图对象
 * Created by FanYanGen on 2019/4/23 19:40
 */
@Data
@ApiModel("出国出境-视图对象")
public class AbroadVO {

    @ApiModelProperty(value = "出国Id")
    private Long abroadId;

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "出国时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date abroadDate;

    @ApiModelProperty(value = "应归时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planReturn;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "实归时间")
    private Date returnDate;

    @ApiModelProperty(value = "前往国家地区")
    @JsonSerialize(using = DictSerializer.class)
    private Long goCountry;

    @ApiModelProperty(value = "党员基本情况")
    private String baseStatus;

    @ApiModelProperty(value = "出国原因")
    @JsonSerialize(using = DictSerializer.class)
    private Long abroadReason;

    @ApiModelProperty(value = "回国情况")
    @JsonSerialize(using = DictSerializer.class)
    private Long returnStatus;

    @ApiModelProperty(value = "出国境定居时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date settleTime;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "党籍处理方式")
    @JsonSerialize(using = DictSerializer.class)
    private Long registryMode;

    @ApiModelProperty(value = "联系情况")
    @JsonSerialize(using = DictSerializer.class)
    private Long linkStatus;

    @ApiModelProperty(value = "恢复组织生活情况")
    @JsonSerialize(using = DictSerializer.class)
    private Long returnActivitiesStatus;

    @ApiModelProperty(value = "申请恢复组织生活日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyActivitiesDate;

    @ApiModelProperty(value = "批准恢复组织生活日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date allowActivitiesDate;

    @ApiModelProperty(value = "申请保留停止党籍时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registryReverseDate;

    @ApiModelProperty(value = "组织关系出境时是否转往国外")
    private Byte isTransOut;

    @ApiModelProperty(value = "创建时间 yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

}
