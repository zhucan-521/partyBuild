package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * desc: 出国信息-视图对象
 * Created by FanYanGen on 2019/4/29 11:01
 */
@Data
@ApiModel("出国信息-视图对象")
public class GoAbroadDetailsVO {

    @ApiModelProperty(value = "出国Id")
    private Long abroadId;

    @ApiModelProperty(value = "组织id")
    private Long orgId;

    @ApiModelProperty(value = "人员Id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "前往国家地区 字典:CGCJ", notes = "字典CGCJ")
    @JsonSerialize(using = DictSerializer.class)
    private Long goCountry;

    @ApiModelProperty(value = "出国原因 CGYY")
    @JsonSerialize(using = DictSerializer.class)
    private Long abroadReason;

    @ApiModelProperty(value = "党籍处理方式 CLQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long registryMode;

    @ApiModelProperty(value = "申请保留停止党籍时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date registryReverseDate;

    @ApiModelProperty(value = "党员基本情况")
    private String baseStatus;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "出国时间")
    private Date abroadDate;

    @ApiModelProperty(value = "联系情况 LXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long linkStatus;

    @ApiModelProperty(value = "出国境定居时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date settleTime;

}
