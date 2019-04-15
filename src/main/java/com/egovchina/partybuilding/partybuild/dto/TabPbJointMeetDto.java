package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeet;
import com.egovchina.partybuilding.partybuild.entity.TabPbJointMeetOrg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/29
 */

@Data
@Accessors(chain = true)
@ApiModel("党建联席会及成员组织")
public class TabPbJointMeetDto extends TabPbJointMeet {

    @ApiModelProperty(value = "党建联席会成员组织列表", required = true)
    List<TabPbJointMeetOrg> meetOrg;
}
