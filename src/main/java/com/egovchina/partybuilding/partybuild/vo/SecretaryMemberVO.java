package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("书记详情显示")
@Data
public class SecretaryMemberVO {

    @ApiModelProperty(value = "id主键")
    private Long secretaryId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "籍贯 码表值 JG")
    @JsonSerialize(using = DictSerializer.class)
    private Long ancestorPlace;

    @ApiModelProperty(value = "部门Id")
    private Long deptId;

    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "出生地")
    private String bornPlace;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "出生日期", example = "yyyy-hh-dd")
    private Date birthday;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间")
    private Date joinTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "工作时间", example = "yyyy-hh-dd")
    private Date workDate;

    @ApiModelProperty(value = "健康状况 码表值 GB4767")
    @JsonSerialize(using = DictSerializer.class)
    private Long health;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "党内职务")
    private List<PositivesVO> positivesVOs;

    @ApiModelProperty(value = "家庭成员")
    private List<FamilyMemberVO> familys;

    @ApiModelProperty(value = "职务，手动录入：如：中共长沙市委基层党建工作领导小组办公室常务副主任")
    private String postive;

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

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任现职时间")
    private Date servingTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任同级实职时间", example = "yyyy-hh-dd")
    private Date servingRealTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任现级时间", example = "yyyy-hh-dd")
    private Date incumbentTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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

    @ApiModelProperty(value = "书记奖励")
    private List<RewardsVO> rewardsVOs;

    @ApiModelProperty(value = "书记惩罚")
    private List<PunishmentVO> punishmentVOs;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

}
