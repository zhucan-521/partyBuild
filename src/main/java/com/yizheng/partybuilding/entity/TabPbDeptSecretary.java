package com.yizheng.partybuilding.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yizheng.partybuilding.dto.PunishmentRewardsDto;
import com.yizheng.partybuilding.system.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@ApiModel(value = "实体")
@Data
public class TabPbDeptSecretary implements Serializable {
    @ApiModelProperty(value = "id主键")
    private Long secretaryId;

    @ApiModelProperty(value = "党员id，非党员也会维护到党员表，只是状态为非党员")
    private Long userId;

    @ApiModelProperty(value = "职务，手动录入：如：中共长沙市委基层党建工作领导小组办公室常务副主任")
    private String postive;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "参加工作时间")
    private Date joinWorkerTime;

    @ApiModelProperty(value = "出生地")
    private String birthAddress;

    @ApiModelProperty(value = "专业技术职称")
    private String professionalTitles;

    @ApiModelProperty(value = "熟悉专业有何专长")
    private String professionalSpecialty;

    @ApiModelProperty(value = "健康状况")
    private String health;

    @ApiModelProperty(value = "学历学位-全日制教育")
    private String fullTimeSchooling;

    @ApiModelProperty(value = "学历学位-在职教育")
    private String education;

    @ApiModelProperty(value = "毕业院校系及专业")
    private String collegeMajor;

    @ApiModelProperty(value = "毕业院校系及专业2")
    private String collegeMajorTwo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任现职时间")
    private Date servingTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任同级实职时间")
    private Date servingRealTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "任现级时间")
    private Date incumbentTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
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

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "删除标记，默认0，删除为1")
    private String delFlag;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "创建用户Id")
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名")
    private String createUsername;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改用户Id")
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名")
    private String updateUsername;

    @ApiModelProperty(value = "简历，用字符串数组保存所有时间段内取得的职称，如：[ {} , {} ]")
    private String resume;

    @ApiModelProperty(value = "近五年培训情况")
    private String trainingSituation;

    @TableField(exist = false)
    @ApiModelProperty(value = "人员信息")
    private SysUser user;

    @TableField(exist = false)
    @ApiModelProperty(value = "家庭成员")
    private List<TabPbFamily> familyList;

    @TableField(exist = false)
    @ApiModelProperty(value = "奖惩情况")
    private List<PunishmentRewardsDto> rewardsDtoList;

    @TableField(exist = false)
    @ApiModelProperty(value = "组织名称")
    private String name;

    @TableField(exist = false)
    @ApiModelProperty(value = "组织范围 0 全组织 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织）")
    private Long orgRange;

    @TableField(exist = false)
    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @TableField(exist = false)
    @ApiModelProperty(value = "名字")
    private String realname;

    @TableField(exist = false)
    @ApiModelProperty(value = "组织id")
    private Long rangeDeptId;
}