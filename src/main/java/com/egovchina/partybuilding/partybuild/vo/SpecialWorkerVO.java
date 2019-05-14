package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 描述:
 *
 * @author wuyunjie
 * Date 2019-04-23 10:56
 */
@Data
@ApiModel(value = "专干VO")
public class SpecialWorkerVO {
    @ApiModelProperty(value = "专干主键")
    private Long specialWorkerId;

    @ApiModelProperty(value = "组织联系人")
    private String contactor;

    @ApiModelProperty(value = "组织联系电话")
    private String contactNumber;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别 码表XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "接任时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date arriveTime;

    @ApiModelProperty(value = "离任时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date leftTime;

    @ApiModelProperty(value = "管理组织id")
    private Long manageOrgId;

    @ApiModelProperty(value = "管理组织名称")
    private String manageOrgName;

    @ApiModelProperty(value = "变更状态，-1离职，1在职")
    private Integer status;

    @ApiModelProperty(value = "所属组织ID")
    private Long deptId;

    @ApiModelProperty(value = "所属组织名称")
    private String deptName;

    @ApiModelProperty(value = "党员Id")
    private Long userId;

    @ApiModelProperty(value = "单位类别 码表值 DWLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long unitProperty;
}
