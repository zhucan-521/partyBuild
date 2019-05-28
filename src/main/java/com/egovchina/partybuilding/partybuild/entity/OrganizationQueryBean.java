package com.egovchina.partybuilding.partybuild.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 组织查询实体
 *
 * @author Zhang Fan
 **/
@ApiModel("组织查询实体")
@Data
public class OrganizationQueryBean {

    @ApiModelProperty("组织编码")
    private String orgCode;

    @ApiModelProperty("所在单位代码")
    private String unitCode;

    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("组织状态")
    private Long orgStatus;

    @ApiModelProperty("书记名称")
    private String orgnizeMaster;

    @ApiModelProperty("组织隶属关系 dict ZZLS 多个用,号拼接")
    private String dependencyRelation;

    @ApiModelProperty("组织类别 dict ZZLB 多个用,号拼接")
    private String orgnizeProperty;

    @ApiModelProperty("单位类型 dict DWLB 多个用,号拼接")
    private String unitProperty;

    @ApiModelProperty("组织范围 1 查当前组织及其直属组织； 2 查当前组织及所有下级组织；其他值 查本组织")
    private String orgRange = "";

    @ApiModelProperty("组织ID")
    private Long rangeDeptId;

    @ApiModelProperty("是否包含统计信息")
    private Boolean containsStatistics = Boolean.FALSE;

    @ApiModelProperty("领域类别 dict LYLB 多个用,号拼接")
    private String domainCategory;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private List<String> domainCategorys;

}
