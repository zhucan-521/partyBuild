package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class TabPbUnitInfo {

    private Long unitId;

    private Long orgId;

    private String unitName;

    private String unitMaster;

    private String unitCode;

    private Byte ifMaster = 0;

    private String belongRelation;

    private String unitProperty;

    private String industry;

    private String economicType;

    private String entScale;

    private String entType;

    private String unitAddress;

    private Byte ifCreateService;

    private Byte ifCreateVoluntTeam;

    private Byte ifInstructionContact;

    private Long instructorCount;

    private Byte ifReorganization;

    private Byte corpIsPartyMember;

    private Byte corpAsGroupSecretary;

    private Long corpOrgnizeStatus;

    private Long goverOrgnizeStatus;

    private Long cityStreetType;

    private Long intermediaryType;

    private Long positiveCount;

    private Long positiveCount35;

    private Long workPositiveCount;

    private Long techPositiveCount;

    private String description;

    private String contactNumber;

    private Long numberOfHousehold;

    private Long householdPopulation;

    private Long permanentPopulation;

    private String firstMasterName;

    private String firstMasterIdCard;

    private Date firstMasterHoldStartTime;

    private Long firstMasterTermOfOffice;

    private Byte ifSuperiorConnection;

    private Byte ifFrontierEthnicArea;

    private Long workCount;

    private Byte ifHasTwoDbOneWy;

    private Byte ifBuildedUnion;

    private Byte unionPrincipalIsPartyMember;

    private Byte ifBuildedGqt;

    private Byte gqtPrincipalIsPartyMember;

    private Byte ifBuildedWomenGroup;

    private Byte womenGroupPrincipalIsPartyMember;

    private Long naturalVillageCount;

    private Long groupAnnualIncome;

    private Byte ifBuildCardPoorVillage;

    private Byte ifBuildedVillageCommittee;

    private Long structureVillageCount;

    private Long communityVillageCount;

    private Long lastYearVillagePerCapitaIncome;

    private Long lastYearAvgSocialWage;

    private Long unitPartyCount;

    private Byte legalPersonIsPartyMember;

    private Long unitLegalPerson;

    private String unitPhone;

    private Byte ifLegalUnit;

    private Long unitRegisterType;

    private Long socialOrgType;

    private Long causeUnitType;

    private Long organUnitType;

    private String eblFlag;

    private String delFlag;

    private Long orderNum;

    private Date createTime;

    private Long createUserid;

    private String createUsername;

    private Date updateTime;

    private Long updateUserid;

    private String updateUsername;

    private Long version;

    private Long partyBuidingStatus;

    private Long partyMemberCount;

}