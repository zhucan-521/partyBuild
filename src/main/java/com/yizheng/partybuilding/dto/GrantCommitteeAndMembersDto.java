package com.yizheng.partybuilding.dto;

import com.yizheng.partybuilding.entity.TabPbGrantCommitteMember;
import com.yizheng.partybuilding.entity.TabPbGrantCommittee;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author chenshanlu
 * @version 1.0
 * @date 2018/12/10
 */

@Data
@Accessors(chain = true)
public class GrantCommitteeAndMembersDto extends TabPbGrantCommittee {
    private List<TabPbGrantCommitteMember> members;
}
