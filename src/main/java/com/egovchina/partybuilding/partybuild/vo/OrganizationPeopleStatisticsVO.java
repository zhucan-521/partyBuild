package com.egovchina.partybuilding.partybuild.vo;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 组织下人数统计
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "组织下人数统计实体")
@Data
public class OrganizationPeopleStatisticsVO implements Serializable {

    @ApiModelProperty(value = "组织ID")
    private Long orgId;

    @ApiModelProperty(value = "组织姓名")
    private String orgName;

    @ApiModelProperty(value = "组织类别 dict ZZLB")
    @JsonSerialize(using = DictSerializer.class)
    private Long orgnizeProperty;

    @ApiModelProperty(value = "联系人")
    private String contactor;

    @ApiModelProperty(value = "联系方式")
    private String contactNumber;

    @ApiModelProperty(value = "正式党员数")
    private Long formalNum;

    @ApiModelProperty(value = "预备党员数")
    private Long prepNum;

    @ApiModelProperty(value = "少数民族数")
    private Long minorityNum;

    @ApiModelProperty(value = "大专以上数")
    private Long collegeOrAboveNum;

    @ApiModelProperty(value = "男数")
    private Long manNum;

    @ApiModelProperty(value = "女数")
    private Long womanNum;
}
