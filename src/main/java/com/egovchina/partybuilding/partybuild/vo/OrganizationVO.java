package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 组织VO
 *
 * @author Zhang Fan
 **/
@ApiModel(value = "组织VO实体")
@Data
public class OrganizationVO {

    @ApiModelProperty(value = "组织ID")
    private Long deptId;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "组织短名称")
    private String orgShortName;

    @ApiModelProperty(value = "组织编码")
    private String orgCode;

    @ApiModelProperty(value = "父组织ID")
    private Long parentId;

    @ApiModelProperty(value = "父组织名称")
    private String parentName;

    @ApiModelProperty(value = "是否是党小组 dict SYSFLAG")
    @JsonSerialize(using = DictSerializer.class)
    private Byte isTeam;

    @ApiModelProperty(value = "分类定等 dict FLDD")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgLevel;

    @ApiModelProperty(value = "定等日期 yyyy-MM-dd", example = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date levelDate;

    @ApiModelProperty(value = "组织状态 dict ZZZT")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgStatus;

    @ApiModelProperty(value = "是否是城市 dict SYSFLAG")
    @JsonSerialize(using = DictSerializer.class)
    private Byte isCity;

    @ApiModelProperty(value = "是否是父结点")
    private Byte isParent;

    @ApiModelProperty(value = "是否是支部")
    private Byte isBranch;

    @ApiModelProperty(value = "组织类别 dict ZZLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgnizeProperty;

    @ApiModelProperty(value = "依赖关系 dict KZ41")
    @JsonSerialize(using = DictSerializer.class)
    private String dependencyRelation;

    @ApiModelProperty(value = "组织书记ID")
    private Long orgnizeMasterId;

    @ApiModelProperty(value = "组织书记名称")
    private String orgnizeMaster;

    @ApiModelProperty(value = "联系人ID")
    private Long contactorId;

    @ApiModelProperty(value = "联系人名称")
    private String contactor;

    @ApiModelProperty(value = "办公面积")
    private Long officeSize;

    @ApiModelProperty(value = "通讯地址")
    private String address;

    @ApiModelProperty(value = "所在单位")
    private String unitName;

    @ApiModelProperty(value = "所在单位编码")
    private String unitCode;

    @ApiModelProperty(value = "所在单位ID")
    private Long unitId;

    @ApiModelProperty(value = "单位类别")
    @JsonSerialize(using = DictSerializer.class)
    private Long unitProperty;

    @ApiModelProperty(value = "所属社区ID")
    @JsonSerialize(using = DictSerializer.class)
    private Long communityAddr;

    @ApiModelProperty(value = "建立日期 yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date foundedDate;

    @ApiModelProperty(value = "建立文号")
    private String foundedFileNumber;

    @ApiModelProperty(value = "联系方式")
    private String contactNumber;

    @ApiModelProperty(value = "传真号码")
    private String faxNumber;

    @ApiModelProperty(value = "邮编")
    private String postCode;

    @ApiModelProperty(value = "组织描述")
    private String description;

    @ApiModelProperty(value = "组织所在坐标")
    private String position;

    @ApiModelProperty(value = "组织范围坐标")
    private String positionRange;

    @ApiModelProperty(value = "建立原因")
    private String foundedReason;

    @ApiModelProperty(value = "党组织所在单位情况")
    @JsonSerialize(using = DictSerializer.class)
    private Long unitState;

    @ApiModelProperty(value = "联点领导ID")
    private Long leaderUserId;

    @ApiModelProperty(value = "结对共建组织")
    private Long partnerOrgId;

    @ApiModelProperty(value = "信息完整度")
    private Integer complete;

    @ApiModelProperty(value = "单位列表")
    private List<UnitInfoVO> units;
}
