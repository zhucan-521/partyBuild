package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.HelpTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.HelpTeamQueryBean;
import com.egovchina.partybuilding.partybuild.vo.HelpTeamMemberVO;
import com.egovchina.partybuilding.partybuild.vo.HelpTeamVO;

import java.util.List;

public interface HelpTeamService {

    /**
     * 新增驻村帮扶队伍
     *
     * @param helpTeamDTO
     * @return
     */
    int addHelpTeam(HelpTeamDTO helpTeamDTO);

    /**
     * 选人接口
     *
     * @param orgId
     * @return
     */
    List<HelpTeamMemberVO> selectHelpTeamMemberVO(Long orgId);

    /**
     * 根据主键删除
     *
     * @param teamId
     * @return
     */
    int deleteHelpTeam(Long teamId);

    /**
     * 列表选择帮扶队伍
     *
     * @param helpTeamQueryBean
     * @return
     */
    List<HelpTeamVO> selectActiveHelpTeam(HelpTeamQueryBean helpTeamQueryBean, Page page);

    /**
     * 根据主键查询帮扶队伍详情
     *
     * @param teamId
     * @return
     */
    HelpTeamVO getHelpTeamVOByTeamId(Long teamId);

    /**
     * 修改帮扶队伍
     *
     * @param helpTeamDTO
     * @return
     */
    int updateHelpTeamDTOByTeamId(HelpTeamDTO helpTeamDTO);

}
