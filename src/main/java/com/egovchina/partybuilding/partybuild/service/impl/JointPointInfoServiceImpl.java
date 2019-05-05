package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.partybuild.dto.LinkLeaderDTO;
import com.egovchina.partybuilding.partybuild.entity.TabPbLinkLeader;
import com.egovchina.partybuilding.partybuild.repository.TabPbLinkLeaderMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.JointPointInfoService;
import com.egovchina.partybuilding.partybuild.vo.LinkLeaderVO;
import com.egovchina.partybuilding.partybuild.vo.UserDeptPositionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;

/**
 * 描述:
 * 联点信息实现类
 *
 * @author wuyunjie
 * Date 2019-04-20 10:38
 */
@Service
public class JointPointInfoServiceImpl implements JointPointInfoService {

    @Autowired
    private TabSysDeptMapper deptMapper;
    @Autowired
    private TabSysUserMapper tabSysUserMapper;
    @Autowired
    private TabPbLinkLeaderMapper tabPbLinkLeaderMapper;

    /**
     * 根据userId查询组织id、职务，联点信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserDeptPositionVO selectJointByUserId(Long userId) {
        return tabSysUserMapper.selectUserDeptPositionVOByUserId(userId);
    }

    /**
     * 根据组织id 获得该组织连接领导列表
     *
     * @param deptId
     * @return
     */
    @Override
    public List<LinkLeaderVO> selectUserDeptByDeptId(Long deptId) {
        if (deptMapper.selectByPrimaryKey(deptId) != null) {
            return tabPbLinkLeaderMapper.selectLinkLeaderVoByDeptId(deptId);
        }
        throw new BusinessDataInvalidException("组织不存在");
    }

    /**
     * 根据联点领导id删除联点信息
     * @param linkLedaerId
     * @return
     */
    @Override
    public int delJointPointInfoByLinkLedaerId(Long linkLedaerId) {
        TabPbLinkLeader tabPbLinkLeader = new TabPbLinkLeader();
        tabPbLinkLeader.setLinkLedaerId(linkLedaerId);
        tabPbLinkLeader.setDelFlag("1");
        paddingUpdateRelatedBaseFiled(tabPbLinkLeader);
        return tabPbLinkLeaderMapper.updateByPrimaryKeySelective(tabPbLinkLeader);
    }

    /**
     * 保存联点信息
     * @param linkLeaderDTO
     * @return
     */
    @Override
    @Transactional
    public int saveJointPointInfo(LinkLeaderDTO linkLeaderDTO) {
        TabPbLinkLeader tabPbLinkLeader = new TabPbLinkLeader();
        BeanUtil.copyPropertiesIgnoreNull(linkLeaderDTO,tabPbLinkLeader);
        int count = 0;
        if (deptMapper.selectByPrimaryKey(linkLeaderDTO.getDeptId()) != null
                && tabSysUserMapper.selectByPrimaryKey(linkLeaderDTO.getUserId()) != null) {
            TabPbLinkLeader record = new TabPbLinkLeader();
            record.setDeptId(linkLeaderDTO.getDeptId());
            record.setUserId(linkLeaderDTO.getUserId());
            BeanUtil.copyPropertiesIgnoreNull(linkLeaderDTO, record);
            List<TabPbLinkLeader> tabPbLinkLeaders = tabPbLinkLeaderMapper.selectByUserIdAndDeptId(record);
            if (tabPbLinkLeaders.size() == 0) {
                count += tabPbLinkLeaderMapper.insertSelective(tabPbLinkLeader);
            } else {
                for (TabPbLinkLeader linkLeader : tabPbLinkLeaders) {
                    record.setLinkLedaerId(linkLeader.getLinkLedaerId());
                    count += tabPbLinkLeaderMapper.updateByPrimaryKeySelective(record);
                }
            }
        } else {
            throw new BusinessDataInvalidException("组织或人员不存在");
        }
//        if (linkLeaderDTO.getActivitiesList() != null) {
//            List<ActivitiesDTO> activitiesList = linkLeaderDTO.getActivitiesList();
//            TabPbParticipant pbParticipant = new TabPbParticipant();
//            pbParticipant.setUserId(linkLeaderDTO.getUserId());
//            pbParticipant.setIfLinkLeader((byte)1);
//            for (ActivitiesDTO activitiesDTO : activitiesList) {
//                if (tabPbActivitiesMapper.selectByActivitiesId(activitiesDTO.getActivitiesId()) != null) {
//                    activitiesDTO.setInviteLinkLeader((byte)1);
//                    TabPbActivities tabPbActivities =
//                            generateTargetCopyPropertiesAndPaddingBaseField(activitiesDTO, TabPbActivities.class,true);
//                    tabPbActivitiesMapper.updateByPrimaryKeySelective(tabPbActivities);
//                    pbParticipant.setActivitiesId(activitiesDTO.getActivitiesId());
//                    pbParticipant.setRealName(tabSysUserMapper.selectByPrimaryKey(linkLeaderDTO.getUserId()).getUsername());
//                    if (tabPbParticipantMapper.selectCountByParticipant(pbParticipant) == 0) {
//                        tabPbParticipantMapper.insertSelective(pbParticipant);
//                    } else {
//                        throw new BusinessDataCheckFailException(activitiesDTO.getSubject() + "，此活动该联点领导已经存在");
//                    }
//                } else {
//                    throw new BusinessDataNotFoundException("此活动不存在");
//                }
//            }
//        }
        return count;
    }
}
