package com.egovchina.partybuilding.partybuild.entity;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class TabPbHelpRecordTeam {

    /**
     * 主键
     */
    private Long recordTeamId;

    /**
     * 记录id
     */
    private Long recordId;

    /**
     * 队伍id
     */
    private Long teamId;
}