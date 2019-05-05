package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@ApiModel(value = "单位实体VO")
public class UnitInfoVO {
    @ApiModelProperty(value = "单位ID")
    private Long unitId;

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "单位名称", required = true)
    @NotBlank(message = "单位名称不能为空")
    private String unitName;

    @ApiModelProperty(value = "单位编码")
    private String unitCode;

    @ApiModelProperty(value = "是否主单位 1是；0否")
    private Byte ifMaster = 0;

    @ApiModelProperty(value = "单位隶属关系 码表值DWLS")
    @JsonSerialize(using = DictSerializer.class)
    private String belongRelation;

    @ApiModelProperty(value = "单位属性 码表值DWLB")
    @JsonSerialize(using = DictSerializer.class)
    private String unitProperty;

    @ApiModelProperty(value = "所属行业 码表值 SSHY")
    @JsonSerialize(using = DictSerializer.class)
    private String industry;

    @ApiModelProperty(value = "经济类型 码表值 JJLX")
    @JsonSerialize(using = DictSerializer.class)
    private String economicType;

    @ApiModelProperty(value = "企业规模 码表值 QYGM")
    @JsonSerialize(using = DictSerializer.class)
    private String entScale;

    @ApiModelProperty(value = "企业类型 码表值QYLX")
    @JsonSerialize(using = DictSerializer.class)
    private String entType;

    @ApiModelProperty(value = "单位地址")
    private String unitAddress;

    @ApiModelProperty(value = "建立党员服务机构标识 1是；0否")
    private Byte ifCreateService;

    @ApiModelProperty(value = "是否建立党员志愿者队伍 1是；0否")
    private Byte ifCreateVoluntTeam;

    @ApiModelProperty(value = "是否党建工作指导联系 1是；0否")
    private Byte ifInstructionContact;

    @ApiModelProperty(value = "党建指导员数量")
    private Long instructorCount;

    @ApiModelProperty(value = "是否改制企业 1是；0否")
    private Byte ifReorganization;

    @ApiModelProperty(value = "法定代表人是否党员 1是；0否")
    private Byte corpIsPartyMember;

    @ApiModelProperty(value = "法定代表人兼任党组书记 1是；0否")
    private Byte corpAsGroupSecretary;

    @ApiModelProperty(value = "法人单位建立党的基层组织情况 码表值DWJLDZZQK")
    @JsonSerialize(using = DictSerializer.class)
    private Long corpOrgnizeStatus;

    @ApiModelProperty(value = "政府部门建立党组织情况 码表值ZFJD")
    @JsonSerialize(using = DictSerializer.class)
    private Long goverOrgnizeStatus;

    @ApiModelProperty(value = "城市街道社区乡镇标识 1是；0否")
    private Long cityStreetType;

    @ApiModelProperty(value = "中介组织类型 码表值 ZJLX")
    @JsonSerialize(using = DictSerializer.class)
    private Long intermediaryType;

    @ApiModelProperty(value = "在岗职工数")
    private Long positiveCount;

    @ApiModelProperty(value = "35岁以下在岗职工数")
    private Long positiveCount35;

    @ApiModelProperty(value = "在岗职工中工人数")
    private Long workPositiveCount;

    @ApiModelProperty(value = "在岗专业技术人员数")
    private Long techPositiveCount;

    @ApiModelProperty(value = "数据描述")
    private String description;

    @ApiModelProperty(value = "联系电话")
    private String contactNumber;

    @ApiModelProperty(value = "户数（户")
    private Long numberOfHousehold;

    @ApiModelProperty(value = "户籍人口（人）")
    private Long householdPopulation;

    @ApiModelProperty(value = "常住人口（人）")
    private Long permanentPopulation;

    @ApiModelProperty(value = "第一书记姓名")
    private String firstMasterName;

    @ApiModelProperty(value = "第一书记公民身份证号")
    private String firstMasterIdCard;

    @ApiModelProperty(value = "第一书记任职开始时间")
    private Date firstMasterHoldStartTime;

    @ApiModelProperty(value = "第一书记任期")
    private Long firstMasterTermOfOffice;

    @ApiModelProperty(value = "是否为上级党建联系点")
    private Byte ifSuperiorConnection;

    @ApiModelProperty(value = "边疆民族地区标识 1是；0否")
    private Byte ifFrontierEthnicArea;

    @ApiModelProperty(value = "在岗职工数")
    private Long workCount;

    @ApiModelProperty(value = "是否有“两代表一委员” 1是；0否")
    private Byte ifHasTwoDbOneWy;

    @ApiModelProperty(value = "是否建立工会 1是；0否")
    private Byte ifBuildedUnion;

    @ApiModelProperty(value = "工会负责人是否是党员 1是；0否")
    private Byte unionPrincipalIsPartyMember;

    @ApiModelProperty(value = "是否建立共青团 1是；0否")
    private Byte ifBuildedGqt;

    @ApiModelProperty(value = "共青团负责人是否是党员 1是；0否")
    private Byte gqtPrincipalIsPartyMember;

    @ApiModelProperty(value = "是否建有妇联 1是；0否")
    private Byte ifBuildedWomenGroup;

    @ApiModelProperty(value = "妇联负责人是否是党员 1是；0否")
    private Byte womenGroupPrincipalIsPartyMember;

    @ApiModelProperty(value = "自然村数（个）")
    private Long naturalVillageCount;

    @ApiModelProperty(value = "集体经济年收入（元）")
    private Long groupAnnualIncome;

    @ApiModelProperty(value = "是否为建档立卡贫困村 1是；0否")
    private Byte ifBuildCardPoorVillage;

    @ApiModelProperty(value = "是否建立村务监督委员会 1是；0否")
    private Byte ifBuildedVillageCommittee;

    @ApiModelProperty(value = "建制村数（个）")
    private Long structureVillageCount;

    @ApiModelProperty(value = "社区数（个）")
    private Long communityVillageCount;

    @ApiModelProperty(value = "上年度当地农村居民人均可支配收入（元）")
    private Long lastYearVillagePerCapitaIncome;

    @ApiModelProperty(value = "上年度当地社会平均工资（元）")
    private Long lastYearAvgSocialWage;

    @ApiModelProperty(value = "单位中工党员数（人）")
    private Long unitPartyCount;

    @ApiModelProperty(value = "法人是否是党员")
    private Byte legalPersonIsPartyMember;

    @ApiModelProperty(value = "单位负责人")
    private Long unitLegalPerson;

    @ApiModelProperty(value = "单位电话号码")
    private String unitPhone;

    @ApiModelProperty(value = "是否法人单位")
    private Byte ifLegalUnit;

    @ApiModelProperty(value = "企业登记注册类型")
    private Long unitRegisterType;

    @ApiModelProperty(value = "社会组织类型")
    private Long socialOrgType;

    @ApiModelProperty(value = "事业单位类型")
    private Long causeUnitType;

    @ApiModelProperty(value = "机关单位类型")
    private Long organUnitType;

    @ApiModelProperty(value = "建立党组织情况")
    private Long partyBuidingStatus;

    @ApiModelProperty(value = "党员人数")
    private Long partyMemberCount;

}