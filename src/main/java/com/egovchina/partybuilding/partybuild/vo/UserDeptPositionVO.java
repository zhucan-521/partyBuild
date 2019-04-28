package com.egovchina.partybuilding.partybuild.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 描述:
 * 人员组织职务DTO
 *
 * @outhor wuyunjie
 * @create 2018-12-03 17:15
 */
@ApiModel(value = "人员组织职务VO")
@Data
public class UserDeptPositionVO {

    @ApiModelProperty(value = "人员id")
    private Long userId;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "组织id")
    private Long deptId;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "人员职务集合")
    private List<PositivesVO> positivesVOList;

    @ApiModelProperty(value = "组织联点领导联点主键")
    private Long linkLedaerId;
}
