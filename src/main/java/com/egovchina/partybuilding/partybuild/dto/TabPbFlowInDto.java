package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowIn;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 描述:
 * 流入党员dto
 *
 * @outhor asd
 * @create 2018-11-28 13:03
 */
@ApiModel(value = "流入党员dto",description = "流入党员dto")
@Data
public class TabPbFlowInDto extends TabPbFlowIn {

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "姓名")
    private String username;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "流往组织名称")
    private String flowToOrgName;


    @ApiModelProperty(value = "原职业 码表ZY")
    @JsonSerialize(using = DictSerializer.class)
    private Long outProfession;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "流出日期")
    private Date flowOutDate;

    @ApiModelProperty(value = "流出组织联系人")
    private String flowFromOrgContactor;

    @ApiModelProperty(value = "流出组织联系电话")
    private String flowFromOrgPhone;

    @ApiModelProperty(value = "流入党支部联系方式")
    private String flowToOrgPhone;

    @ApiModelProperty(value = "流入党支部联系人")
    private String flowToOrgContactor;

    @ApiModelProperty(value = "0流出日期大于当前日期，1流出日期小于等于当前日期")
    private Long isEnableAccept;




}
