package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.common.entity.TabPbAttachment;
import com.egovchina.partybuilding.partybuild.entity.TabPbLeadTeam;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author Jiang An
 **/
@ApiModel("领导班子Dto")
@Data
public class TabPbLeadTeamDto extends TabPbLeadTeam {

    @ApiModelProperty(value = "组织范围 1 当前组织（包括一级下级组织）2当前组织（包含所有下级组织） 其他值 当前组织")
    private Long orgRange;

    @ApiModelProperty(value = "范围组织ID")
    private Long rangeDeptId;

    @ApiModelProperty(value = "身份证")
    private String idCardNo;

    @ApiModelProperty(value = "姓名")
    private String personName;

    @ApiModelProperty(value = "组织书记")
    private String orgNiZeMaster;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "班子成员数")
    private Integer classNum;

    @ApiModelProperty(value = "图片附件数量")
    private Integer imgNum;

    @ApiModelProperty(value = "文档附件数量")
    private Integer docNum;

    @ApiModelProperty(value = "附件")
    private List<TabPbAttachment> attachments;

    @ApiModelProperty(value = "换届时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date changeDates;
}
