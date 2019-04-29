package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel("党建联席会成员组织")
public class JointMeetOrgDTO {
    @ApiModelProperty(value = "成员组织主键")
    private Long memberOrgId;

    @ApiModelProperty(value = "党建联席会主键")
    @NotNull(message = "党建联席会主键不能为空")
    private Long jointMeetId;

    @ApiModelProperty(value = "组织主键")
    private Long orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "单位id")
    private Long unitId;

    @ApiModelProperty(value = "加入日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date joinDate;

}