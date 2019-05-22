package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author Jiang An
 **/
@Data
@ApiModel(value = "签到情况列表")
public class SignInToListVO {

    @ApiModelProperty("人员Id")
    private Long participantId;

    @ApiModelProperty("userId")
    private Long userId;

    @ApiModelProperty("组织名称")
    private String name;

    @ApiModelProperty("身份证号码")
    private String idCardNo;

    @ApiModelProperty("人员姓名")
    private String realName;

    @ApiModelProperty("签到时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date signinTime;

    @ApiModelProperty("签到状态")
    private Long signInState;

}
