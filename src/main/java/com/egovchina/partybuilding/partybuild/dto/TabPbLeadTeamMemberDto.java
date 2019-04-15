package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.config.DictSerializer;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeamMember;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 * 领导班子成员DTO
 *
 * @outhor asd
 * @create 2018-12-04 14:50
 */
@ApiModel("领导班子成员DTO")
@Data
public class TabPbLeadTeamMemberDto extends TabPbLeadTeamMember {

    @ApiModelProperty(value = "成员性别 dict XB")
    @JsonSerialize(using = DictSerializer.class)
    private Long gender;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "组织名称")
    private String deptName;

    @ApiModelProperty(value = "身份证号码")
    private String idCardNo;

    @ApiModelProperty(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织")
    private Long orgRange;

    @ApiModelProperty(value = "范围组织ID")
    private Long rangeDeptId;

}
