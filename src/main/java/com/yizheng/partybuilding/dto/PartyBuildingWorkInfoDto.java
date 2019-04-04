package com.yizheng.partybuilding.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yizheng.commons.config.DictSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 党建工作信息Dto
 *
 * @Author Zhang Fan
 **/
@ApiModel(value = "党建工作信息实体")
@Data
public class PartyBuildingWorkInfoDto {

    @ApiModelProperty(value = "党组织隶属关系 dict KZ41")
    @JsonSerialize(using = DictSerializer.class)
    private String dependencyRelation;

    @ApiModelProperty(value = "党组织书记是否参加培训 1是；0否")
    private Byte masterJoinedTrain;

    @ApiModelProperty(value = "建立党组织情况")
    private Long partyBuidingStatus;

    @ApiModelProperty(value = "党组织班子成员数")
    private Integer leadTeamMemberCount;

    @ApiModelProperty(value = "党组织班子成员交叉任职人数")
    private Integer leadTeamMemberCrossCount;

    @ApiModelProperty(value = "是否软弱涣散基层党组织 1是；0否")
    private Byte ifWeaknessOrg;

    @ApiModelProperty(value = "是否换届 1是；0否")
    private Byte ifChange;

    @ApiModelProperty(value = "专/兼职党务工作者人数")
    private Integer specialWorkerCount;

    @ApiModelProperty(value = "是否召开民主生活会或组织生活会 1是；0否")
    private Byte ifHeldALifeMeeting;

    @ApiModelProperty(value = "是否进行党员党性定期分析和民主评议中共党员工作 1是；0否")
    private Byte ifGetOnDemocraticReview;

}
