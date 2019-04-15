package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/14
 */
@Data
@Accessors(chain = true)
@ApiModel("发展党员信息")
public class DevPartyUserDto {
    private Long dpId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("当前发展状态")
    private Long status;

    @ApiModelProperty("所属组织名称")
    private String orgName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;


    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "固定号码")
    private String fixPhone;

    @ApiModelProperty(value = "工作单位")
    private Long unitId;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "聘用专业技术职务 码表值 PYZYJSZW")
    @JsonSerialize(using = DictSerializer.class)
    private Long technician;

    @ApiModelProperty(value = "工作岗位")
    @JsonSerialize(using = DictSerializer.class)
    private String jobPosition;
}
