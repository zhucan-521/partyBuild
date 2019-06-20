package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("书记概况")
@Data
public class SecretarysDetailsVO {
    //书记的信息
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "首次进入两委")
    private Date firstCommitteesDate;

    @ApiModelProperty(value = "有何特长")
    private String professionalSpecialty;

    @ApiModelProperty(value = "专业技术职务")
    private String professionalTitles;

    @ApiModelProperty(value = "学历学位-全日制教育")
    private String fullTimeSchooling;

    @ApiModelProperty(value = "学历学位-在职教育")
    private String education;

    @ApiModelProperty(value = "毕业院校系及专业-全日制")
    private String collegeMajor;

    @ApiModelProperty(value = "毕业院校系及专业-在职教育")
    private String collegeMajorTwo;

    @ApiModelProperty(value = "原任职务")
    @JsonSerialize(using = DictSerializer.class)
    private Long oldPosition;

    @ApiModelProperty(value = "现任职务")
    @JsonSerialize(using = DictSerializer.class)
    private Long newPosition;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任职时间")
    private Date serveTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任免时间")
    private Date appointmentTime;
}
