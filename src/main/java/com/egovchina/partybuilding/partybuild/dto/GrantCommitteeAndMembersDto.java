package com.egovchina.partybuilding.partybuild.dto;

import com.egovchina.partybuilding.partybuild.entity.TabPbGrantCommitteMember;
import com.egovchina.partybuilding.partybuild.entity.TabPbGrantCommittee;
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
