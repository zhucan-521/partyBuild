package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.entity.HelpTeamQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbHelpTeam;
import com.egovchina.partybuilding.partybuild.vo.HelpTeamVO;

import java.util.List;

public interface TabPbHelpTeamMapper {
    int deleteByPrimaryKey(Long teamId);

    int insert(TabPbHelpTeam record);

    int insertSelective(TabPbHelpTeam record);

    TabPbHelpTeam selectByPrimaryKey(Long teamId);

    int updateByPrimaryKeySelective(TabPbHelpTeam record);

    int updateByPrimaryKey(TabPbHelpTeam record);

    /**
     * 列表查询驻村帮扶队伍
     *
     * @param helpTeamQueryBean
     * @return
     */
    List<HelpTeamVO> selectiveHelpTeamVOs(HelpTeamQueryBean helpTeamQueryBean);

    /**
     * 查询驻村帮扶队伍
     *
     * @param teamId
     * @return
     */
    HelpTeamVO getHelpTeamVOByTeamId(Long teamId);
}