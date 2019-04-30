package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * desc: 回国信息-视图对象
 * Created by FanYanGen on 2019/4/29 11:02
 */
@Data
@ApiModel("回国信息-视图对象")
public class BackAbroadDetailsVO {

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

    @ApiModelProperty(value = "回国情况 HGQK", notes = "字典")
    @JsonSerialize(using = DictSerializer.class)
    private Long returnStatus;

    @ApiModelProperty(value = "应归时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planReturn;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "实归时间")
    private Date returnDate;

    @ApiModelProperty(value = "恢复组织生活情况 HFZZSHQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long returnActivitiesStatus;

    @ApiModelProperty(value = "申请恢复组织生活日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date applyActivitiesDate;

    @ApiModelProperty(value = "批准恢复组织生活日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date allowActivitiesDate;

    @ApiModelProperty(value = "组织关系出境时是否转往国外")
    private Byte isTransOut;

    @ApiModelProperty(value = "备注")
    private String comment;

}
