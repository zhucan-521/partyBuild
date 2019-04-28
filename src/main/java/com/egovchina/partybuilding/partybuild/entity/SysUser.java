package com.egovchina.partybuilding.partybuild.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.partybuild.entity.TabPbPositives;
import com.egovchina.partybuilding.partybuild.entity.TabPbUserTag;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@ApiModel(value = "系统用户实体")
@Data
@TableName("sys_user")
@Accessors(chain = true)
@Alias("sysUserPartyInfo")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "用户ID")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;


    @ApiModelProperty(value = "身份证号")
    private String username;

    @ApiModelProperty(value = "用户名")
    private String realname;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "随机盐")
    @JsonIgnore
    private String salt;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间", hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "0-正常，1-删除")
    @TableLogic
    private String delFlag;

    @ApiModelProperty(value = "电话号码")
    private String phone;

    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 后台用户没有ci此组织id，只有管理组织id
     */
    @ApiModelProperty(value = "组织ID ,党支部Id")
    private Integer deptId;

    /**
     * 后台用户保存管理组织id
     */
    @ApiModelProperty(value = "后台用户管理组织ID")
    private Integer manageDeptId;

    @ApiModelProperty(value = "微信openid")
    private String wxOpenid;

    @ApiModelProperty(value = "QQ openid")
    private String qqOpenid;

    @JsonSerialize(using = DictSerializer.class)
    @ApiModelProperty(value = "人员类别 码表值 RYLB")
    private Long identityType;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "曾用名")
    private String usedName;

    @ApiModelProperty(value = "性别 码表值 XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @JsonIgnore
    @TableField(exist = false)
    private String genderName;

    @ApiModelProperty(value = "民族 码表值 MZ")
    @JsonSerialize(using = DictSerializer.class)
    private Long nation;

    @JsonIgnore
    @TableField(exist = false)
    private String nationName;

    @ApiModelProperty(value = "户口所在地")
    private String registeredresidence;

    @ApiModelProperty(value = "籍贯 码表值 JG")
    @JsonSerialize(using = DictSerializer.class)
    private Long ancestorPlace;

    @JsonIgnore
    @TableField(exist = false)
    private String ancestorPlaceName;

    @ApiModelProperty(value = "出生地")
    private String bornPlace;

    @ApiModelProperty(value = "健康状况 码表值 GB4767")
    @JsonSerialize(using = DictSerializer.class)
    private Long health;

    @JsonIgnore
    @TableField(exist = false)
    private String healthName;

    @ApiModelProperty(value = "家庭出身 码表值 JTCS")
    @JsonSerialize(using = DictSerializer.class)
    private Long familyorigin;

    @JsonIgnore
    @TableField(exist = false)
    private String familyoriginName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @ApiModelProperty(value = "婚姻状况 码表值 FYZK")
    @JsonSerialize(using = DictSerializer.class)
    private Long maritalStatus;

    @JsonIgnore
    @TableField(exist = false)
    private String maritalStatusName;

    @ApiModelProperty(value = "工作单位")
    private Long unitId;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "工作时间")
    private Date workDate;

    @ApiModelProperty(value = "固定号码")
    private String fixPhone;

    @ApiModelProperty(value = "学历 码表值 WHCD")
    @JsonSerialize(using = DictSerializer.class)
    private Long education;

    @JsonIgnore
    @TableField(exist = false)
    private String educationName;

    @ApiModelProperty(value = "学位 码表值 XW")
    @JsonSerialize(using = DictSerializer.class)
    private Long degree;

    @JsonIgnore
    @TableField(exist = false)
    private String degreeName;

    @ApiModelProperty(value = "家庭住址")
    private String familyAddress;

    @ApiModelProperty(value = "居住地所在社区   码表值XZQH")
    @JsonSerialize(using = DictSerializer.class)
    private Long communityAddr;

    @JsonIgnore
    @TableField(exist = false)
    private String communityAddrName;

    @ApiModelProperty(value = "专业")
    private Long profession;

    @ApiModelProperty(value = "职务级别 dict ZWJB")
    @JsonSerialize(using = DictSerializer.class)
    private Long positive;

    @JsonIgnore
    @TableField(exist = false)
    private String positiveName;

    @ApiModelProperty(value = "民主党派")
    private Long democraticParty;

    @ApiModelProperty(value = "聘用专业技术职务 码表值 PYZYJSZW")
    @JsonSerialize(using = DictSerializer.class)
    private Long technician;

    @JsonIgnore
    @TableField(exist = false)
    private String technicianName;

    @ApiModelProperty(value = "农民工 0-是，1-否")
    private String migrant;

    @ApiModelProperty(value = "毕业院校")
    private String graduatedFrom;

    @ApiModelProperty(value = "联系情况 码表值 LXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long contact;

    @JsonIgnore
    @TableField(exist = false)
    private String contactName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "加入其他党团时间")
    private Date joinelsetime;

    @ApiModelProperty(value = "户口所在地派出所")
    private String policeOffice;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "退休时间")
    private Date retiredTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "离岗时间")
    private Date postTime;

    @ApiModelProperty(value = "照片")
    private String portrait;

    @ApiModelProperty(value = "个人身份")
    private Long personIdentity;

    @ApiModelProperty(value = "新阶层 码表值 NEWCLASS")
    @JsonSerialize(using = DictSerializer.class)
    private Long stratum;

    @JsonIgnore
    @TableField(exist = false)
    private String stratumName;

    @ApiModelProperty(value = "一线情况 码表值 YXQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long frontLine;

    @JsonIgnore
    @TableField(exist = false)
    private String frontLineName;

    @ApiModelProperty(value = "QQ号码")
    private String qqNumber;

    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @ApiModelProperty(value = "党员编号")
    private String pmCode;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入党时间、预备党员时间")
    private Date joinTime;


    @DateTimeFormat(pattern = "yyyy-MM-dd" )
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value = "转正时间、正式党员时间")
    private Date regularTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "党龄")
    private Integer partyStanding;

    @ApiModelProperty(value = "转正情况 码表值 ZZQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long regularStatus;

    @JsonIgnore
    @TableField(exist = false)
    private String regularStatusName;

    @ApiModelProperty(value = "发展类型 码表值 FZLX")
    @JsonSerialize(using = DictSerializer.class)
    private Long developType;

    @JsonIgnore
    @TableField(exist = false)
    private String developTypeName;

    @ApiModelProperty(value = "入党介绍人")
    private String reference;

    @ApiModelProperty(value = "党费缴纳基数")
    private BigDecimal feeRatio;

    @ApiModelProperty(value = "是否27年7月前入团、8月后入党")
    private Byte joinAfterAugust;

    @ApiModelProperty(value = "贫困党员标志")
    private Byte isPoor;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "失联时间")
    private Date lostTime;

    @ApiModelProperty(value = "是否失联")
    private Byte isLlost;

    @ApiModelProperty(value = "流动状态 码表值 LDZT")
    @JsonSerialize(using = DictSerializer.class)
    private Long flowStatus;

    @JsonIgnore
    @TableField(exist = false)
    private String flowStatusName;

    @ApiModelProperty(value = "流来组织主键")
    private Long flowFromOrgId;

    @ApiModelProperty(value = "流来组织名称")
    private String flowFromOrgName;

    @ApiModelProperty(value = "流来组织联系人")
    private String flowFromOrgContactor;

    @ApiModelProperty(value = "流来组织联系电话")
    private String flowFromOrgPhone;

    @ApiModelProperty(value = "流往组织主键")
    private Long flowToOrgId;

    @ApiModelProperty(value = "流往组织名称")
    private String flowToOrgName;

    @ApiModelProperty(value = "流往组织联系人")
    private String flowToOrgContactor;

    @ApiModelProperty(value = "流往组织联系电话")
    private String flowToOrgPhone;

    @ApiModelProperty(value = "政治面貌 码表值 ZZMM")
    @JsonSerialize(using = DictSerializer.class)
    private Long policyFace;

    @JsonIgnore
    @TableField(exist = false)
    private String policyFaceName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "加入时间")
    private Date joinTime2;

    @ApiModelProperty(value = "曾任职务")
    private String positived;

    @ApiModelProperty(value = "入党联系人主键")
    private Long joinContactId;

    @ApiModelProperty(value = "入党联系人")
    private String contactor;

    @ApiModelProperty(value = "申请日期")
    private Date appDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预计积极分子日期")
    private Date expActivistDate;

    @ApiModelProperty(value = "确认积极分子日期、入党积极分子时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date activistDate;

    @ApiModelProperty(value = "预计发展对象日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expDevDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "确认发展对象日期、发展对象时间")
    private Date devDate;

    @ApiModelProperty(value = "发展阶段 码表值 SYSFZLX")
    @JsonSerialize(using = DictSerializer.class)
    private Long devStage;

    @JsonIgnore
    @TableField(exist = false)
    private String devStageName;

    /**
     * 党籍相关字段
     */

    @ApiModelProperty(value = "入党所在支部主键")
    private Long joinOrgId;

    @ApiModelProperty(value = "入党所在支部名称")
    private String joinOrgName;

    @ApiModelProperty(value = "加入党组织类别")
    private Long joinOrgType;

//    @ApiModelProperty(value = "加入党组织时间")
//    @JsonFormat(pattern = "yyyy-MM-dd")
//    private Long joinOrgTime;

    @ApiModelProperty(value = "入党组织联系人")
    private String joinOrgContactor;

    @ApiModelProperty(value = "入党组织联系电话")
    private String joinOrgPhone;

    @ApiModelProperty(value = "是否为本单位发展")
    private Byte isSelfDev;

    @TableField(exist = false)
    @ApiModelProperty(value = "恢复党组织时间")
    private Date addTime;

    @ApiModelProperty(value = "有效标记", hidden = true)
    @JsonIgnore
    private String eblFlag;

    @ApiModelProperty(value = "排序码")
    private Long orderNum;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "创建用户Id", hidden = true)
    private Long createUserid;

    @ApiModelProperty(value = "创建人姓名", hidden = true)
    private String createUsername;

    @ApiModelProperty(value = "修改用户Id", hidden = true)
    private Long updateUserid;

    @ApiModelProperty(value = "修改用户姓名", hidden = true)
    private String updateUsername;

    @ApiModelProperty(value = "版本", hidden = true)
    @JsonIgnore
    private Long version;

    @ApiModelProperty(value = "离岗原因")
    private String postReason;

    @ApiModelProperty(value = "工作简历")
    private String workResumes;

    @ApiModelProperty(value = "备注")
    private String comment;

    @ApiModelProperty(value = "申请书内容")
    private String content;

    @ApiModelProperty(value = "奖惩情况 dict DYJC")
    @JsonSerialize(using = DictSerializer.class)
    private String rewards;

    @JsonIgnore
    @TableField(exist = false)
    private String rewardsName;

    @ApiModelProperty(value = "现时表现")
    private String presentPerform;

    @ApiModelProperty(value = "是否台湾籍")
    private Byte isTaiwaner;

    @ApiModelProperty(value = "档案管理单位主键")
    private Long filesManageUnitId;

    @ApiModelProperty(value = "档案管理单位名称")
    private String filesManageUnit;

    @ApiModelProperty(value = "党籍 0无、1刚入党、2转正、3出党、4停止党籍、5死亡、6其他、 7发展中的党员")
    @JsonSerialize(using = DictSerializer.class)
    private Long registryStatus;

    @ApiModelProperty(value = "党籍中文")
    @TableField(exist = false)
    private String registryStatusName;

    @TableField(exist = false)
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private List<Long> status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出党时间")
    @TableField(exist = false)
    private Date reduceTime;

    @ApiModelProperty(value = "出党方式 3出党、4停止党籍、5死亡、6其他")
    @TableField(exist = false)
    private Long outType;

    @TableField(exist = false)
    @ApiModelProperty(value = "从属组织")
    private String subordinateDeptName;

    @ApiModelProperty(value = "报到组织主键")
    private Integer reportOrgId;

    @ApiModelProperty(value = "报到组织名称")
    private String reportOrgName;

    @ApiModelProperty(value = "报到组织联系人")
    private String reportOrgContactor;

    @ApiModelProperty(value = "报到组织联系电话")
    private String reportOrgPhone;

    @ApiModelProperty(value = "个人兴趣爱好")
    private String likes;

    @TableField(exist = false)
    @ApiModelProperty(value = "困难类型 dict KNLX")
    @JsonSerialize(using = DictSerializer.class)
    private Long hardshipType;

    @JsonIgnore
    @TableField(exist = false)
    private String hardshipTypeName;

    @ApiModelProperty(value = "工作岗位 dict GZGW")
    @JsonSerialize(using = DictSerializer.class)
    private String jobPosition;

    @JsonIgnore
    @TableField(exist = false)
    private String jobPositionName;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户标签列表")
    private List<TabPbUserTag> tabPbUserTags;

    @TableField(exist = false)
    private String inWhereDeptName;

    @TableField(exist = false)
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @TableField(exist = false)
    @ApiModelProperty(value = "基础资料完成度")
    private Integer complete;

    @TableField(exist = false)
    @ApiModelProperty(value = "人员职务集合")
    private List<TabPbPositives> positiveList;

    @TableField(exist = false)
    @ApiModelProperty(value = "人员排序")
    private Long orderNumUser;
}
