package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Accessors(chain = true)
@Alias("sysUserPartyInfo")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String username;

    private String realname;

    private String password;

    private String salt;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String delFlag;

    private String phone;

    private String avatar;

    private Long deptId;

    private Integer manageDeptId;

    private String wxOpenid;

    private String qqOpenid;

    private Long identityType;

    private String idCardNo;

    private String usedName;

    private Long gender;

    private String genderName;

    private Long nation;

    private String nationName;

    private String registeredresidence;

    private Long ancestorPlace;

    private String ancestorPlaceName;

    private String bornPlace;

    private Long health;

    private String healthName;

    private Long familyorigin;

    private String familyoriginName;

    private Date birthday;

    private Long maritalStatus;

    private String maritalStatusName;

    private Long unitId;

    private String unitName;

    private Date workDate;

    private String fixPhone;

    private Long education;

    private String educationName;

    private Long degree;

    private String degreeName;

    private String familyAddress;

    private Long communityAddr;

    private String communityAddrName;

    private Long profession;

    private Long positive;

    private String positiveName;

    private Long democraticParty;

    private Long technician;

    private String technicianName;

    private String migrant;

    private String graduatedFrom;

    private Long contact;

    private String contactName;

    private Date joinelsetime;

    private String policeOffice;

    private Date retiredTime;

    private Date postTime;

    private String portrait;

    private Long personIdentity;

    private Long stratum;

    private String stratumName;

    private Long frontLine;

    private String frontLineName;

    private String qqNumber;

    private String email;

    private String pmCode;

    private Date joinTime;

    private Date regularTime;

    private Integer partyStanding;

    private Long regularStatus;

    private String regularStatusName;

    private Long developType;

    private String developTypeName;

    private String reference;

    private BigDecimal feeRatio;

    private Byte joinAfterAugust;

    private Byte isPoor;

    private Date lostTime;

    private Byte isLlost;

    private Long flowStatus;

    private String flowStatusName;

    private Long flowFromOrgId;

    private String flowFromOrgName;

    private String flowFromOrgContactor;

    private String flowFromOrgPhone;

    private Long flowToOrgId;

    private String flowToOrgName;

    private String flowToOrgContactor;


    private String flowToOrgPhone;

    private Long policyFace;

    private String policyFaceName;

    private Date joinOrgTime;

    private String positived;

    private Long joinContactId;

    private String contactor;

    private Date appDate;

    private Date expActivistDate;

    private Date activistDate;

    private Date expDevDate;

    private Date devDate;

    private Long devStage;

    private String devStageName;

    private Long joinOrgId;

    private String joinOrgName;

    private Long joinOrgType;

    private String joinOrgContactor;

    private String joinOrgPhone;

    private Byte isSelfDev;

    private Date addTime;

    private String eblFlag;

    private Long orderNum;

    private String description;

    private Long createUserid;

    private String createUsername;

    private Long updateUserid;

    private String updateUsername;

    private Long version;

    private String postReason;

    private String workResumes;

    private String comment;

    private String content;

    private String rewards;

    private String rewardsName;

    private String presentPerform;

    private Byte isTaiwaner;

    private Long filesManageUnitId;

    private String filesManageUnit;

    private Long registryStatus;

    private String registryStatusName;

    private Date reduceTime;

    private Long outType;

    private String subordinateDeptName;

    private Integer reportOrgId;

    private String reportOrgName;

    private String reportOrgContactor;

    private String reportOrgPhone;

    private String likes;

    private Long hardshipType;

    private String hardshipTypeName;

    private String jobPosition;

    private String jobPositionName;

    private String inWhereDeptName;

    private Integer age;

    private Integer complete;

    private Long orderNumUser;
}
