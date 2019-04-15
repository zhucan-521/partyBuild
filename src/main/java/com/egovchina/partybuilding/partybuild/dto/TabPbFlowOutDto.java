package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.partybuild.entity.TabPbFlowOut;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "流出党员扩展类")
@Data
public class TabPbFlowOutDto extends TabPbFlowOut {

    @ApiModelProperty(value = "流入地名称")
    private String flowOutOrgName;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "流出组织联系人")
    private String flowFromOrgContactor;

    @ApiModelProperty(value = "流出组织联系电话")
    private String flowFromOrgPhone;

    @ApiModelProperty(value = "列表范围 0查所有；1 查当前组织及其直属组织； 2 查当前组织及所有下级组织")
    private String orgRange;

    @ApiModelProperty(value = "组织ID")
    private Long rangeDeptId;

    @ApiModelProperty(value = "流入时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date flowInDate;

    @ApiModelProperty(value = "流入组织名称")
    @NotNull(message = "请选择流入组织名称！")
    private String flowToOrgName;


    @ApiModelProperty(value = "流出组织名称")
    @NotNull(message = "请选择流出组织名称！")
    private String flowFromOrgName;



}