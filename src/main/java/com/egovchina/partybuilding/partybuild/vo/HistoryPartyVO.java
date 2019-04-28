package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "l历史党员查询实体")
@Data
public class HistoryPartyVO {
    @ApiModelProperty(value = "申请人姓名")
    private String realname;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "组织ID ,党支部Id")
    private Integer deptId;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @TableField(exist = false)
    @ApiModelProperty(value = "党龄")
    private Integer partyStanding;

    @ApiModelProperty(value = "党籍 0无、1刚入党、2转正、3出党、4停止党籍、5死亡、6其他、 7发展中的党员")
    @JsonSerialize(using = DictSerializer.class)
    private Long registryStatus;

    @ApiModelProperty(value = "原所在支部")
    private String inWhereDeptName;

}