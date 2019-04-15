package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.system.entity.SysDept;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织dto包含统计信息
 *
 * @Author Zhang Fan
 **/
@Data
@ApiModel("组织（包含统计信息）实体")
public class SysDeptDtoWithCountInfo extends SysDept {

    @ApiModelProperty(value = "男党员数")
    private Long man;

    @ApiModelProperty(value = "女党员数")
    private Long woman;

    @ApiModelProperty(value = "正式党员数")
    private Long formal;

    @ApiModelProperty(value = "预备党员数")
    private Long prep;

    @ApiModelProperty(value = "积极分子数")
    private Long activity;

    @ApiModelProperty(value = "发展对象数")
    private Long growing;

    @ApiModelProperty(value = "少数民族党员数")
    private Long minority;

    @ApiModelProperty(value = "两新党员数")
    private Long twoNew;

    @ApiModelProperty(value = "退役军人党员数")
    private Long decommissioning;

    @ApiModelProperty(value = "大专及以上学历党员数")
    private Long collegeAndAbove;

    @ApiModelProperty(value = "党委数")
    private Long partyWei;

    @ApiModelProperty(value = "总支部数")
    private Long partyZBranch;

    @ApiModelProperty(value = "支部数")
    private Long partyBranch;

    @ApiModelProperty(value = "街道党组织数")
    private Long streetOrg;

    @ApiModelProperty(value = "社区党组织数")
    private Long communityOrg;

    @ApiModelProperty(value = "乡镇党组织数")
    private Long townshipOrg;

    @ApiModelProperty(value = "建制村党组织数")
    private Long villageOrg;

    @ApiModelProperty(value = "共有经济组织数")
    private Long commonEconomyOrg;

    @ApiModelProperty(value = "非共有经济组织数")
    private Long unCommonEconomyOrg;

    @ApiModelProperty(value = "事业法人单位党组织")
    private Long causeLegalEntityOrg;

    @ApiModelProperty(value = "民办非公企业党组数")
    private Long privateNonPublicOrg;

    @ApiModelProperty(value = "机关法人单位党组织数")
    private Long organLegalEntityOrg;

    @ApiModelProperty(value = "社会组织党组织数")
    private Long societyOrg;

    @ApiModelProperty(value = "社团民非单位党组织数")
    private Long societyNonEntityOrg;
}
