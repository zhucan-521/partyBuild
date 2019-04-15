package com.egovchina.partybuilding.partybuild.entity;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author chenshanlu
 */
@Data
@Accessors(chain = true)
public class TabPbAbroad {

    @ApiModelProperty(hidden = true)
    private static final String DATA_EXAMPLE = "2018-10-30";
    @ApiModelProperty(hidden = true)
    private static final String FORMAT = "yyyy-MM-dd";

    @ApiModelProperty(value = "出国Id")
    private Long abroadId;

    @ApiModelProperty(value = "组织id")
    @NotNull(message = "组织ID不能为空")
    private Long orgId;

    @ApiModelProperty(value = "人员Id")
    @NotNull(message = "人员ID不能为空")
    private Long userId;

    @ApiModelProperty(value = "范围")
    private Long orgRange;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;


    @ApiModelProperty(value = "出国时间", example = DATA_EXAMPLE)
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date abroadDate;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "应归时间", example = DATA_EXAMPLE)
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date planReturn;

    @ApiModelProperty(value = "实归时间", example = DATA_EXAMPLE)
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date returnDate;

    @ApiModelProperty(value = "前往国家地区 字典:CGCJ", notes = "字典CGCJ")
    @NotNull(message = "出境国家不能为空")
    @JsonSerialize(using = DictSerializer.class)
    @NotNull(message = "出境国家不能为空")
    private Long goCountry=null;

    @ApiModelProperty(value = "党员基本情况")
    private String baseStatus;

    @ApiModelProperty(value = "出国原因 CGYY")
    @JsonSerialize(using = DictSerializer.class)
    private Long abroadReason;

    @ApiModelProperty(value = "回国情况 HGQK", notes = "字典")
    @JsonSerialize(using = DictSerializer.class)
    private Long returnStatus;

    @ApiModelProperty(value = "出国境定居时间", example = DATA_EXAMPLE)
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date settleTime;

    @ApiModelProperty(value = "有效标记", hidden = true)
    @JsonIgnore
    private String eblFlag;

    @ApiModelProperty(value = "删除标记", hidden = true)
    @JsonIgnore
    private String delFlag;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述", hidden = true)
    @JsonIgnore
    private String description;

    @ApiModelProperty(value = "创建时间", hidden = true, example = DATA_EXAMPLE)
    @JsonIgnore
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id", hidden = true)
    @JsonIgnore
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名", hidden = true)
    @JsonIgnore
    private String createUsername;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @JsonIgnore
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id", hidden = true)
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名", hidden = true)
    @JsonIgnore
    private String updateUsername;

    @ApiModelProperty(value = "版本", hidden = true)
    @JsonIgnore
    private Long version;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "党籍处理方式 CLQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long registryMode;


    @ApiModelProperty(value = "联系情况 LXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long linkStatus;


    @ApiModelProperty(value = "恢复组织生活情况 HFZZSHQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long returnActivitiesStatus;


    @ApiModelProperty(value = "申请恢复组织生活日期", example = DATA_EXAMPLE)
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date applyActivitiesDate;


    @ApiModelProperty(value = "批准恢复组织生活日期", example = DATA_EXAMPLE)
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date allowActivitiesDate;


    @ApiModelProperty(value = "申请保留停止党籍时间", example = DATA_EXAMPLE)
    @DateTimeFormat(pattern = FORMAT)
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date registryReverseDate;


    @ApiModelProperty(value = "组织关系出境时是否转往国外")
    private Byte isTransOut;

}