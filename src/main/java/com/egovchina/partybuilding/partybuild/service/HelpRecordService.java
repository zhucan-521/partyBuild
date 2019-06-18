package com.egovchina.partybuilding.partybuild.service;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.partybuild.dto.HelpRecordDTO;
import com.egovchina.partybuilding.partybuild.dto.HelpRecordTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.HelpRecordQueryBean;
import com.egovchina.partybuilding.partybuild.vo.HelpRecordVO;


import java.util.List;

public interface HelpRecordService {

    /**
     * 选择队伍接口
     *
     * @param orgId
     * @return
     */
    List<HelpRecordTeamDTO> selectHelpRecordTeamDTO(Long orgId);

    /**
     * 添加帮扶记录
     *
     * @param helpRecordDTO
     * @return
     */
    int addHelpRecord(HelpRecordDTO helpRecordDTO);

    /**
     * 删除帮扶记录
     *
     * @param recordId
     * @return
     */
    int removeHelpRecord(Long recordId);

    /**
     * 编辑帮扶记录
     *
     * @param helpRecordDTO
     * @return
     */
    int editHelpRecord(HelpRecordDTO helpRecordDTO);

    /**
     * 帮扶记录列表
     *
     * @param helpRecordQueryBean
     * @return
     */
    List<HelpRecordVO> selectiveHelpRecords(HelpRecordQueryBean helpRecordQueryBean, Page page);

    /**
     * 帮扶记录详情
     *
     * @param recordId
     * @return
     */
    HelpRecordVO getHelpRecordVOByRecordId(Long recordId);
}
