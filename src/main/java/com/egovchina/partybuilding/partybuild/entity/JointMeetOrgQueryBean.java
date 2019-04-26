package com.egovchina.partybuilding.partybuild.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 * 联席会查询实体
 *
 * @author wuyunjie
 * Date 2019-04-25 9:51
 */
@Data
@ApiModel(value = "联席会查询实体")
public class JointMeetOrgQueryBean {
    @ApiModelProperty(value = "联席会主键")
    private Long jointMeetId;

    @ApiModelProperty(value = "联席会成员主键")
    private Long orgId;

    @ApiModelProperty(value = "联席会成员名称")
    private String unitName;
}
