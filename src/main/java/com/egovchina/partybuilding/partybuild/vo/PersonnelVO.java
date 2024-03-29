package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("人员实体用于党员身份核查返回")
@Data
public class PersonnelVO {
    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "性别 dict XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "类型 dict USERTAG")
    @JsonSerialize(using = DictSerializer.class)
    private String type;

    @ApiModelProperty(value = "身份证号")
    private String idCardNo;

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "组织联系人")
    private String contactor;

    @ApiModelProperty(value = "组织联系方式")
    private String contactNumber;

    @TableField(exist = false)
    @ApiModelProperty(value = "恢复党组织时间")
    private Date addTime;
}
