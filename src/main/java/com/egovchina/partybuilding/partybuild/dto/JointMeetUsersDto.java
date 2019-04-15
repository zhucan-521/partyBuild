package com.egovchina.partybuilding.partybuild.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2019/01/05
 */

@Data
@Accessors(chain = true)
@ApiModel("联席会成员人员")
public class JointMeetUsersDto {

    @ApiModelProperty("职务")
    private Long positiveId;

    @ApiModelProperty("职务名称")
    private String positiveName;

    @ApiModelProperty("单位id")
    private Long unitId;

    @ApiModelProperty("单位名称")
    private String unitName;

    @ApiModelProperty("组织id")
    private Long orgId;

    @ApiModelProperty("组织名称")
    private String orgName;

    @ApiModelProperty(value = "人员id")
    private Long userId;

    @ApiModelProperty("人员姓名")
    private String userName;

    @ApiModelProperty("身份证")
    private String idCardNo;

    @ApiModelProperty("数据Id")
    private Long participantId;
}
