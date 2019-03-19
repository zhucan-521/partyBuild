package com.yizheng.partybuilding.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@ApiModel(value = "流入党员对象", description = "流入党员实体")
@Data
@Accessors(chain = true)
@TableName("tab_pb_flow_in")
public class TabPbFlowIn {
    @ApiModelProperty(value = "流入Id")
    private Long flowInId;

    @ApiModelProperty(value = "组织主键",required = true)
    private Long orgId;

    @ApiModelProperty(value = "人员Id",required = true)
    private Long userId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "流入时间",example = "yyyy-MM-dd")
    private Date flowInDate;

    @ApiModelProperty(value = "流入类别 码表值 LDQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowInType;

    @ApiModelProperty(value = "流出范围 码表LCFW")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowInRange;

    @ApiModelProperty(value = "流入原因")
    @JsonSerialize(using = DictSerializer.class)
    private String flowInReason;

    @ApiModelProperty(value = "原所在地")
    private String oldPlace;

    @ApiModelProperty(value = "原单位名称")
    private String oldUnitName;

    @ApiModelProperty(value = "原组织")
    private Long oldOrgnizeId;

    @ApiModelProperty(value = "流动证号")
    private String oldOrgnizeCode;

    @ApiModelProperty(value = "原组织名称")
    private String oldOrgnizeName;

    @ApiModelProperty(value = "原党组织电话")
    private String oldOrgnizePhone;

    @ApiModelProperty(value = "原党组织联系人及联系方式 用 , 隔开")
    private String oldOrgnizeContactor;

    @ApiModelProperty(value = "原联系电话")
    private String oldContactPhone;

    @ApiModelProperty(value = "原单位邮政编码")
    private String oldUnitPostcode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "回归时间")
    private Date returnDate;

    @ApiModelProperty(value = "回归标识")
    private Byte returnTag;

    @ApiModelProperty(value = "对应流出id")
    private Long flowOutId;


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

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "数据描述")
    private String description;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    @ApiModelProperty(value = "流入状态 码表值LDGC")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowInState;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "失联时间")
    private Date lostTime;

    @ApiModelProperty(value = "流入状态 码表值DZZLXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long linkStatus;

}