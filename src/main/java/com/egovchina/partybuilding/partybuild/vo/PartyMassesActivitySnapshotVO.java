package com.egovchina.partybuilding.partybuild.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 党群活动人员快照VO
 * @author: HuFan
 * @date: 2019/6/26 22:52
 */
@ApiModel(value = "党群活动人员快照VO")
@Data
@Accessors(chain = true)
public class PartyMassesActivitySnapshotVO {

    @ApiModelProperty(value = "党群活动id")
    private Long partyMassesActivityId;

    @ApiModelProperty(value = "当时所有待参与人快照")
    private String candidateMemberSnapshot;

    @JsonIgnore
    @ApiModelProperty(value = "待参与人对象")
    private CandidateMemberVO candidateMember;

}
