package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.HelpRecordDTO;
import com.egovchina.partybuilding.partybuild.dto.HelpRecordTeamDTO;
import com.egovchina.partybuilding.partybuild.entity.HelpRecordQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbHelpRecord;
import com.egovchina.partybuilding.partybuild.repository.TabPbHelpRecordMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbHelpRecordTeamMapper;
import com.egovchina.partybuilding.partybuild.service.HelpRecordService;
import com.egovchina.partybuilding.partybuild.vo.HelpRecordVO;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class HelpRecordServiceImpl implements HelpRecordService {

    @Autowired
    TabPbHelpRecordMapper tabPbHelpRecordMapper;

    @Autowired
    TabPbHelpRecordTeamMapper tabPbHelpRecordTeamMapper;

    /**
     * 选择队伍根据orgId
     *
     * @param orgId
     * @return
     */
    @Override
    public List<HelpRecordTeamDTO> selectHelpRecordTeamDTO(Long orgId) {
        return tabPbHelpRecordMapper.selectHelpRecordTeamDTO(orgId);
    }

    /**
     * 添加帮扶记录
     *
     * @param helpRecordDTO
     * @return
     */
    @Override
    public int addHelpRecord(HelpRecordDTO helpRecordDTO) {
        TabPbHelpRecord tabPbHelpRecord = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(helpRecordDTO, TabPbHelpRecord.class, false);
        int flag = tabPbHelpRecordMapper.insertSelective(tabPbHelpRecord);
        Long recordId = tabPbHelpRecord.getRecordId();
        if (flag > 0) {
            List<HelpRecordTeamDTO> helpRecordTeamDTOS = helpRecordDTO.getHelpRecordTeams();
            helpRecordTeamDTOS.forEach(item -> item.setRecordId(recordId));
            tabPbHelpRecordTeamMapper.batchInsertTabPbHelpRecordTeam(helpRecordTeamDTOS);
        }
        return flag;
    }

    /**
     * 删除帮扶记录
     *
     * @param recordId
     * @return
     */
    @Override
    public int removeHelpRecord(Long recordId) {
        TabPbHelpRecord tabPbHelpRecord = new TabPbHelpRecord().setRecordId(recordId).setDelFlag(CommonConstant.STATUS_DEL);
        PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled(tabPbHelpRecord);
        int flag = tabPbHelpRecordMapper.updateByPrimaryKeySelective(tabPbHelpRecord);
        if (flag > 0) {
            tabPbHelpRecordTeamMapper.deleteByRecordId(recordId);
        }
        return flag;
    }

    /**
     * 编辑帮扶记录
     *
     * @param helpRecordDTO
     * @return
     */
    @Override
    public int editHelpRecord(HelpRecordDTO helpRecordDTO) {
        TabPbHelpRecord tabPbHelpRecord = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(helpRecordDTO, TabPbHelpRecord.class, true);
        int flag = tabPbHelpRecordMapper.updateByPrimaryKeySelective(tabPbHelpRecord);
        if (flag > 0) {
            tabPbHelpRecordTeamMapper.deleteByRecordId(helpRecordDTO.getRecordId());
            List<HelpRecordTeamDTO> helpRecordTeamDTOS = helpRecordDTO.getHelpRecordTeams();
            helpRecordTeamDTOS.forEach(item -> item.setRecordId(helpRecordDTO.getRecordId()));
            tabPbHelpRecordTeamMapper.batchInsertTabPbHelpRecordTeam(helpRecordTeamDTOS);
        }
        return flag;
    }

    /**
     * 帮扶记录列表
     *
     * @param helpRecordQueryBean
     * @return
     */
    @Override
    public List<HelpRecordVO> selectiveHelpRecords(HelpRecordQueryBean helpRecordQueryBean, Page page) {
        PageHelper.startPage(page);
        return tabPbHelpRecordMapper.selectiveHelpRecordVO(helpRecordQueryBean);
    }

    /**
     * 帮扶记录详情
     *
     * @param recordId
     * @return
     */
    @Override
    public HelpRecordVO getHelpRecordVOByRecordId(Long recordId) {
        return tabPbHelpRecordMapper.getHelpRecordVOByRecordId(recordId);
    }

}
