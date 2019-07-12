package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.annotations.Update;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@ApiModel("书记DTO")
public class SecretaryMemberDTO {

    @ApiModelProperty(value = "id主键")
    @NotNull(message = "secretaryId主键不能为空", groups = Update.class)
    private Long secretaryId;

    @ApiModelProperty(value = "领导班子主键")
    @NotNull(message = "领导班子主键不能为空", groups = Update.class)
    private Long leadTeamId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "组织主键")
    private Long deptId;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "籍贯 码表值 JG")
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
    @ApiModelProperty(value = "入党时间、预备党员时间", example = "yyyy-hh-dd")
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
    @NotNull(message = "现任职务不能为空", groups = Update.class)
    private Long newPosition;

    @ApiModelProperty("职务级别 dict JB")
    @JsonSerialize(using = DictSerializer.class)
    private Long rank;

    @ApiModelProperty(value = "现任职务名称")
    private String newPositionName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任免时间", example = "yyyy-hh-dd")
    @NotNull(message = "任免时间不能为空", groups = Update.class)
    private Date appointmentTime;

    @ApiModelProperty(value = "原任职务")
    @NotNull(message = "原任职务不能为空", groups = Update.class)
    private Long oldPosition;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任职时间", example = "yyyy-hh-dd")
    @NotNull(message = "任职时间不能为空", groups = Update.class)
    private Date serveTime;

    @ApiModelProperty(value = "微信公众号头像")
    private String avatar2;

    @ApiModelProperty(value = "简历", notes = "简历，用字符串数组保存所有时间段内取得的职称，如：[ {} , {} ]")
    private List<ResumeDTO> resumes;

}
