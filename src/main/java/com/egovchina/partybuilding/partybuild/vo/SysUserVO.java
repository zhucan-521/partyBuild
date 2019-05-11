package com.egovchina.partybuilding.partybuild.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel("查看单个党员详情VO")
@Data
public class SysUserVO {
    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "组织ID ,党支部Id")
    private Long deptId;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @ApiModelProperty(value = "居住地所在社区   码表值XZQH")
    @JsonSerialize(using = DictSerializer.class)
    private Long communityAddr;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "固定号码")
    private String fixPhone;

    @ApiModelProperty(value = "家庭住址")
    private String familyAddress;

    @TableField(exist = false)
    @ApiModelProperty(value = "入党所在支部名称")
    private String joinOrgName;

    @ApiModelProperty(value = "曾任职务")
    private String positived;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "人员类别 码表值 RYLB")
    private Long identityType;

    @ApiModelProperty(value = "档案管理单位")
    @JsonSerialize(using = DictSerializer.class)
    private String filesManageUnit;

    @ApiModelProperty(value = "党籍 0无、1刚入党、2转正、3出党、4停止党籍、5死亡、6其他、 7发展中的党员")
    @JsonSerialize(using = DictSerializer.class)
    private Long registryStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间")
    private Date joinTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "加入党组织时间")
    private Date joinOrgTime;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "转正时间、正式党员时间")
    private Date regularTime;

    @ApiModelProperty(value = "工作简历")
    private String workResumes;

    @ApiModelProperty(value = "是否困难")
    private Byte isPoor;

    @ApiModelProperty(value = "是否失联")
    private Byte isLlost;

    @ApiModelProperty(value = "流动状态 码表值 LDZT")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowStatus;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户标签列表")
    private List<UserTagVO> tabPbUserTags;

    @ApiModelProperty(value = "婚姻状况 码表值 FYZK")
    @JsonSerialize(using = DictSerializer.class)
    private Long maritalStatus;

    @ApiModelProperty(value = "健康状况 码表值 GB4767")
    @JsonSerialize(using = DictSerializer.class)
    private Long health;

    @ApiModelProperty(value = "籍贯 码表值 JG")
    @JsonSerialize(using = DictSerializer.class)
    private Long ancestorPlace;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "工作时间")
    private Date workDate;

    @ApiModelProperty(value = "党组织所在社区   码表值XZQH")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgAddrName;

    @ApiModelProperty(value = "是否台湾籍")
    private Byte isTaiwaner;

    @ApiModelProperty(value = "是否农民工")
    private Byte migrant;
}
