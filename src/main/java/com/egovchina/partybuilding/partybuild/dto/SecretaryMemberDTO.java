package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbFamily;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositives;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class SecretaryMemberDTO {

    @ApiModelProperty(value = "id主键")
    private Long secretaryId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "职务，手动录入：如：中共长沙市委基层党建工作领导小组办公室常务副主任")
    private String postive;

    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "籍贯 码表值 JG")
    private Long ancestorPlace;

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @ApiModelProperty(value = "民族 码表值 MZ")
    private Long nation;

    @ApiModelProperty(value = "性别 码表值 XB")
    private Long gender;

    @ApiModelProperty(value = "出生地")
    private String bornPlace;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间")
    private Date joinTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "工作时间")
    private Date workDate;

    @ApiModelProperty(value = "健康状况 码表值 GB4767")
    private Long health;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "奖惩情况 dict DYJC")
    private String rewards;

    @ApiModelProperty(value = "党内职务")
    private List<TabPbPositives> positivesList;

    @ApiModelProperty(value = "家庭成员")
    private List<TabPbFamily> familyList;

    //----------

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

    @ApiModelProperty(value = "是否委员 0是1否")
    private Long whetherMember;

    @ApiModelProperty(value = "是否书记 0是1否")
    private Long whetherSecretary;

    @ApiModelProperty(value = "简历，用字符串数组保存所有时间段内取得的职称，如：[ {} , {} ]")
    private String resume;

    @ApiModelProperty(value = "近五年培训情况")
    private String trainingSituation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任现职时间")
    private Date servingTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任同级实职时间")
    private Date servingRealTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任现级时间")
    private Date incumbentTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "任同一班子同级实职时间")
    private Date incumbentRealTime;

    @ApiModelProperty(value = "近三年度考核情况")
    private String assessmentSituation;

    @ApiModelProperty(value = "竞争性选拨情况")
    private String selectionSituation;

    @ApiModelProperty(value = "破格提拔情况")
    private String promotionSituation;

    @ApiModelProperty(value = "军转干部情况")
    private String armyCadresSituation;

    @ApiModelProperty(value = "后备干部情况")
    private String reserveCadresSituation;

    @ApiModelProperty(value = "两代表-委员职务")
    private String committeeDuties;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;
}
