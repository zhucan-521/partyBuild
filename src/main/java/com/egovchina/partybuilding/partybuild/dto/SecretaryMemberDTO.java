package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class SecretaryMemberDTO {

    @ApiModelProperty(value = "id主键")
    private Long secretaryId;

    @ApiModelProperty(value = "组织Id")
    private Long deptId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "籍贯 码表值 JG")
    @JsonSerialize(using = DictSerializer.class)
    private Long ancestorPlace;

    @ApiModelProperty(value = "民族 码表值 MZ")
    private Long nation;

    @ApiModelProperty(value = "性别 码表值 XB")
    private Long gender;

    @ApiModelProperty(value = "出生地")
    private String bornPlace;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期", example = "yyyy-hh-dd")
    private Date birthday;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间")
    private Date joinTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "工作时间", example = "yyyy-hh-dd")
    private Date workDate;

    @ApiModelProperty(value = "健康状况 码表值 GB4767")
    @JsonSerialize(using = DictSerializer.class)
    private Long health;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "学历学位-全日制教育")
    private String fullTimeSchooling;

    @ApiModelProperty(value = "学历学位-在职教育")
    private String education;

    @ApiModelProperty(value = "毕业院校系及专业-全日制")
    private String collegeMajor;

    @ApiModelProperty(value = "毕业院校系及专业-在职教育")
    private String collegeMajorTwo;

    @ApiModelProperty(value = "专业技术职称")
    private String professionalTitles;

    @ApiModelProperty(value = "熟悉专业有何专长")
    private String professionalSpecialty;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "首次进入两委班子时间", example = "yyyy-hh-dd")
    private Date firstCommitteesDate;

    @ApiModelProperty(value = "现任职务")
    private Long  newPosition;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任免时间", example = "yyyy-hh-dd")
    private Date appointmentTime;

    @ApiModelProperty(value = "原任职务")
    private Long oldPosition;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任职时间", example = "yyyy-hh-dd")
    private Date serveTime;
}
