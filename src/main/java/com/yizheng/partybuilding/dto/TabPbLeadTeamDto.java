package com.yizheng.partybuilding.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yizheng.commons.domain.TabPbAttachment;
import com.yizheng.partybuilding.entity.TabPbLeadTeam;
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

    @ApiModelProperty(hidden = true)
    private static final String format = "yyyy-MM-dd";
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

    @ApiModelProperty(value = "图片集合")
    private List<TabPbAttachment> pictures;

    @ApiModelProperty(value = "文档集合")
    private List<TabPbAttachment> documents;

    @ApiModelProperty(value = "换届时间", example = "yyyy-MM-dd")
    @DateTimeFormat(pattern = format)
    @JsonFormat(pattern = format)
    private Date changeDates;
}
