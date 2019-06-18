package com.egovchina.partybuilding.partybuild.repository;

import com.egovchina.partybuilding.partybuild.dto.HelpRecordTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.HelpRecordQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbHelpRecord;
import com.egovchina.partybuilding.partybuild.vo.HelpRecordVO;

import java.util.List;

public interface TabPbHelpRecordMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(TabPbHelpRecord record);

    int insertSelective(TabPbHelpRecord record);

    TabPbHelpRecord selectByPrimaryKey(Long recordId);

    int updateByPrimaryKeySelective(TabPbHelpRecord record);

    int updateByPrimaryKeyWithBLOBs(TabPbHelpRecord record);

    int updateByPrimaryKey(TabPbHelpRecord record);

    /**
     * 选择帮扶队伍根据orgId
     *
     * @param orgId
     * @return
     */
    List<HelpRecordTeamDTO> selectHelpRecordTeamDTO(Long orgId);

    /**
     * 帮扶记录列表
     *
     * @param helpRecordQueryBean
     * @return
     */
    List<HelpRecordVO> selectiveHelpRecordVO(HelpRecordQueryBean helpRecordQueryBean);

    /**
     * 根据recordId查询帮扶记录详情
     *
     * @param recordId
     * @return
     */
    HelpRecordVO getHelpRecordVOByRecordId(Long recordId);
}