package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("党员信息VO")
@Data
public class PartyMemberInformationVO {

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "组织ID ,党支部Id")
    private Integer deptId;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "用户标签列表")
    private List<TabPbUserTag> tabPbUserTags;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @TableField(exist = false)
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "入党时间、预备党员时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date joinTime;

    @ApiModelProperty(value = "转正时间、正式党员时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date regularTime;

    @ApiModelProperty(value = "报到组织名称")
    private String reportOrgName;

    @ApiModelProperty(value = "从属组织")
    private String subordinateDeptName;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "人员类别 码表值 RYLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long identityType;

    @ApiModelProperty(value = "婚姻状况 码表值 FYZK")
    @JsonSerialize(using = DictSerializer.class)
    private Long maritalStatus;

    @ApiModelProperty(value = "学历 码表值 WHCD")
    @JsonSerialize(using = DictSerializer.class)
    private Long education;

    @ApiModelProperty(value = "工作单位")
    private Long unitId;

    @ApiModelProperty(value = "流动状态 码表值 LDZT")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowStatus;

    @ApiModelProperty(value = "是否失联")
    private Byte isLlost;

    @ApiModelProperty(value = "是否台湾籍")
    private Byte isTaiwaner;

    @ApiModelProperty(value = "基础资料完成度")
    private Integer complete;

    @ApiModelProperty(value = "农民工 0-是，1-否")
    private String migrant;

    @ApiModelProperty(value = "新阶层 码表值 NEWCLASS")
    @JsonSerialize(using = DictSerializer.class)
    private Long stratum;

    @ApiModelProperty(value = "一线情况 码表值 YXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long frontLine;

    @ApiModelProperty(value = "工作时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date workDate;

    @ApiModelProperty(value = "出生日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "籍贯 码表值 JG")
    @JsonSerialize(using = DictSerializer.class)
    private Long ancestorPlace;

    @ApiModelProperty(value = "党籍 0无、1刚入党、2转正、3出党、4停止党籍、5死亡、6其他、 7发展中的党员")
    @JsonSerialize(using = DictSerializer.class)
    private Long registryStatus;

    @ApiModelProperty(value = "家庭住址")
    private String familyAddress;

    @ApiModelProperty(value = "居住地所在社区   码表值XZQH")
    @JsonSerialize(using = DictSerializer.class)
    private Long communityAddr;

    @ApiModelProperty(value = "工作岗位 dict GZGW")
    @JsonSerialize(using = DictSerializer.class)
    private String jobPosition;
}
