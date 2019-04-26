package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("党建联席会成员组织VO")
public class JointMeetOrgVO {
    private static final String FORMAT = "yyyy-MM-dd";

    @ApiModelProperty(value="成员组织主键")
    private Long memberOrgId;

    @ApiModelProperty(value="党建联席会主键", required = true)
    private Long jointMeetId;

    @ApiModelProperty(value="组织主键")
    private Long orgId;

    @ApiModelProperty(value="组织名称")
    private String orgName;

    @ApiModelProperty(value="单位名称")
    private String unitName;

    @ApiModelProperty(value="单位id")
    private Long unitId;

    @ApiModelProperty(value="加入日期")
    @JsonFormat(pattern = FORMAT,timezone = "GMT+8")
    private Date joinDate;

}