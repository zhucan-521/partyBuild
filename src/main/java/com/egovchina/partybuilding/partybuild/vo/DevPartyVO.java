package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * desc: 发展党员-视图对象
 * Created by FanYanGen on 2019/4/23 16:52
 */
@Data
@ApiModel("发展党员-视图对象")
public class DevPartyVO {

    @ApiModelProperty("发展主键")
    private Long dpId;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("发展对象")
    private Long status;

    @ApiModelProperty("组织名")
    private String orgName;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("身份证号")
    private String idCardNo;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("固定电话")
    private String fixPhone;

    @ApiModelProperty(value = "工作单位")
    private Long unitId;

    @ApiModelProperty(value = "工作名称")
    private String unitName;

    @ApiModelProperty("性别")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty("技术")
    @JsonSerialize(using = DictSerializer.class)
    private Long technician;

    @ApiModelProperty("工作地址")
    @JsonSerialize(using = DictSerializer.class)
    private String jobPosition;

}
