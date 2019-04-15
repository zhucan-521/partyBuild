package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.*;
import com.egovchina.partybuilding.partybuild.entity.TabPbActivities;
import com.egovchina.partybuilding.partybuild.entity.TabPbParticipant;
import com.egovchina.partybuilding.partybuild.repository.TabPbActivitiesMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbParticipantMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.PartyOrganizationActivitiesService;
import com.egovchina.partybuilding.partybuild.service.TabPbActivitiesService;
import com.egovchina.partybuilding.partybuild.service.TabSysDeptService;
import com.egovchina.partybuilding.partybuild.system.entity.SysUser;
import com.github.pagehelper.PageHelper;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Jiang An
 **/
@Service
public class PartyOrganizationActivitiesServiceImpl implements PartyOrganizationActivitiesService {

    @Autowired
    private TabPbActivitiesMapper activitiesMapper;
    @Autowired
    private TabPbParticipantMapper pbParticipantMapper;
    @Autowired
    private TabPbActivitiesService tabPbActivitiesService;
    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;
    @Autowired
    private TabSysDeptService tabSysDeptService;
    @Autowired
    private TabSysUserMapper userMapper;
    @Transactional
    @PaddingBaseField
    @Override
    public int insertSelective(PartyOrganizationActivitiesDto activitiesDto) {
        int retVal = activitiesMapper.insertSelective(activitiesDto);
        if (retVal > 0) {
            List<TabPbParticipant> tabPbParticipantList = activitiesDto.getTabPbParticipant();
            //插入人员表
            for (TabPbParticipant tabPbParticipant : tabPbParticipantList) {
                tabPbParticipant.setActivitiesId(activitiesDto.getActivitiesId());
                tabPbParticipant.setActivitytype(String.valueOf(activitiesDto.getActivitiesType()));
                retVal += pbParticipantMapper.insertSelective(tabPbParticipant);
            }
            retVal += modifyAnnexInfo(activitiesDto);
        }
        return retVal;
    }

    /**
     * 维护附件信息
     *
     * @param activitiesDto
     * @return
     */
    private int modifyAnnexInfo(PartyOrganizationActivitiesDto activitiesDto) {
        return iTabPbAttachmentService.intelligentOperation(activitiesDto.getAnnexs(),
                activitiesDto.getActivitiesId(), activityTypeTransformToAttType(activitiesDto.getActivitiesType()));
    }

    @Override
    public PartyOrganizationActivitiesDto findDetails(Long activitiesId, Long activitiesType) {
        PartyOrganizationActivitiesDto details = activitiesMapper.findDetails(activitiesId, activityTypeTransformToAttType(activitiesType));
        if (details != null){
         activitiesMapper.updateOrderNum(activitiesId);
        }
        return details;
    }

    /**
     * 活动类型转换成附件业务类型
     *
     * @param activitiesType
     * @return
     */
    private Long activityTypeTransformToAttType(Long activitiesType) {
        Long attType;
        switch (activitiesType.intValue()) {
            case 59055:
            case 59056:
            case 59057:
            case 59058:
                attType = AttachmentType.ThreeWillBeALesson;
                break;
            case 59131:
                attType = AttachmentType.EcoLife;
                break;
            case 59148:
                attType = AttachmentType.Pairing;
                break;
            case 59145:
                attType = AttachmentType.volunteer;
                break;
            case 59390:
            case 59392:
            case 59393:
            case 59394:
            case 59395:
            case 59396:
            case 59397:
                attType = AttachmentType.PartyWork;
                break;
            case 59146:
                attType = AttachmentType.OtherActivities;
                break;
            case 59147:
                attType = AttachmentType.JOINT_MEET;
                break;
            case 59060:
                attType = AttachmentType.ThematicPartyDay;
                break;
            default:
                throw new BusinessDataInvalidException("活动类型无效");
        }
        return attType;
    }

    //逻辑删除
    @Override
    public int delete(Long activitiesId) {
        return activitiesMapper.deleteId(activitiesId);
    }

    //条件分页查询
    @Override
    public List<PartyOrganizationActivitiesDto> selectWithConditions(Map<String, Object> conditions, Page page) {
        if (conditions.containsKey("activitiesType")) {
            String activitiesType = conditions.get("activitiesType").toString();
            conditions.put("attType", activityTypeTransformToAttType(Long.valueOf(activitiesType)));
        } else { //三会一课
            conditions.put("attType", AttachmentType.ThreeWillBeALesson);
        }
        PageHelper.startPage(page);
        List<PartyOrganizationActivitiesDto> list = activitiesMapper.selectWithConditions(conditions);
        return list;
    }

    //修改
    @PaddingBaseField
    @Transactional
    @Override
    public int updateWithAboutInfo(PartyOrganizationActivitiesDto activitiesDto) {
        //首先修改组织活动表
        int retVal = activitiesMapper.updateByPrimaryKeySelective(activitiesDto);
        if (retVal > 0) {
            //在修改修改人员表
            retVal += tabPbActivitiesService.updatePartyTable(activitiesDto);
            retVal += modifyAnnexInfo(activitiesDto);
        }
        return retVal;
    }

    @Override
    public int signByQRCode(Long activitiesId, String idCardNo) {
        //首先判断这个党员是否在这个活动中存在
        TabPbParticipant tabPbParticipant = pbParticipantMapper.validateUserExists(activitiesId, idCardNo);
        if (tabPbParticipant != null) {
            if (tabPbParticipant.getSigninTime() != null) {
                throw new BusinessDataCheckFailException("该参会人员已签到，请勿重复操作");
            }
            TabPbParticipant update = new TabPbParticipant();
            update.setParticipantId(tabPbParticipant.getParticipantId());
            update.setSigninTime(new Date());
            return pbParticipantMapper.updateByPrimaryKeySelective(update);
        }
        throw new BusinessDataCheckFailException("该参会人员不存在");
    }

    @Transactional
    @PaddingBaseField(recursive = true)
    @Override
    public int insertPairWithAbout(PartyOrganizationActivitiesDto activitiesDto) {

        //把选中的组织ID修改当前组织的partner_org_id字段
        int retVal = tabSysDeptService.pushPairOrgIdToDeptTable(activitiesDto.getPairOrgId(), activitiesDto.getOrgId());
        if (retVal > 0) {
            List<TabPbParticipant> tabPbParticipantList = activitiesDto.getTabPbParticipant();
            //应到人数
            activitiesDto.setDueCount(Long.valueOf(tabPbParticipantList.size()));
            //实到人数
            long factCount = tabPbParticipantList.stream()
                    .filter(tabPbParticipant -> tabPbParticipant.getAbsentReason() == null).count();
            activitiesDto.setFactCount(factCount);
            //插入数据到活动表
            retVal += activitiesMapper.insertSelective(activitiesDto);
            //添加
            for (TabPbParticipant participant : tabPbParticipantList) {
                participant.setActivitiesId(activitiesDto.getActivitiesId());
                participant.setActivitytype("59148"); //结对共建
                pbParticipantMapper.insertSelective(participant);
            }
            retVal += modifyAnnexInfo(activitiesDto);
        }
        return retVal;
    }

    @Override
    public List<Personnel> selectCandidateMemberList(Map<String, Object> conditions) {
        return activitiesMapper.selectCandidateMemberListWithConditions(conditions);
    }

    @PaddingBaseField
    @Override
    public int review(TabPbActivities tabPbActivities) {
        tabPbActivities.setAuditUser(UserContextHolder.getUserIdLong());
        tabPbActivities.setAuditOrg(UserContextHolder.getOrgId());
        tabPbActivities.setAuditTime(new Date());
        return activitiesMapper.review(tabPbActivities);
    }

    @Transactional
    @PaddingBaseField
    @Override
    public int updatePairWithAbout(PartyOrganizationActivitiesDto activitiesDto) {
        //首先修改组织活动表
        int retVal = activitiesMapper.updateByPrimaryKeySelective(activitiesDto);
        if (retVal > 0) {
            retVal += tabPbActivitiesService.updatePartyTable(activitiesDto);
            retVal += iTabPbAttachmentService.intelligentOperation(activitiesDto.getAnnexs(),
                    activitiesDto.getActivitiesId(), activityTypeTransformToAttType(activitiesDto.getActivitiesType()));
        }
        return retVal;
    }

    @Override
    public List<TabPbActivitiesDto> selectPairingListWithConditions(HashMap<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        List<TabPbActivitiesDto> list = activitiesMapper.selectPairingListWithConditions(conditions);
        return list;
    }

    @Override
    public PartyOrganizationActivitiesDto selectPairingDetail(Long activitiesId) {
        return activitiesMapper.selectPairingDetail(activitiesId);
    }

    @Override
    public TabPbActivities selectOneById(Long activitiesId) {
        return activitiesMapper.selectOneById(activitiesId);
    }

    @Override
    public ActivityExclusionParticipantDto selectExclusionMemberList(Long orgId, Long pairOrgId) {
        return activitiesMapper.selectExclusionMemberList(orgId, pairOrgId);
    }

    @Override
    public List<JointMeetUsersDto> selectJoinMeetActivitiesPreUserListWithConditions(Map<String, Object> conditions, Page page) {
        PageHelper.startPage(page);
        return activitiesMapper.selectJoinMeetActivitiesPreUserListWithConditions(conditions);
    }

    /**
     * 查看活动已签到跟未签到人的姓名
     *
     * @param activitiesId
     * @return
     */
    @Override
    public List<PartyOrganizationActivitiesDto> selectCheckInList(Long activitiesId, Long signType, Page page) {
        PageHelper.startPage(page);
        List<PartyOrganizationActivitiesDto> list = pbParticipantMapper.selectCheckInList(activitiesId, signType);
        return list;
    }

    /**
     * @param activitiesId
     * @param participantId
     * @return
     */
    @Override
    public int updateSignIn(Long activitiesId, Long participantId) {
        return pbParticipantMapper.updateSignIn(activitiesId, participantId);
    }

    @Override
    public List<PartyOrganizationActivitiesDto> selectListByUserId(Long userId, Page page) {
        PageHelper.startPage(page);
        return activitiesMapper.selectListByUserId(userId);
    }

    /**
     * 置顶功能
     * @param activitiesId
     * @return
     */
    @Override
    public int stick(Long activitiesId) {
        Long stickNum=activitiesMapper.findByOrderNum();
        return activitiesMapper.stick(activitiesId,stickNum);
    }

    /**
     * 取消置顶功能
     * @param activitiesId
     * @return
     */
    @Override
    public int deleteStick(Long activitiesId) {
        return activitiesMapper.deleteStick(activitiesId);
    }

    /**
     * 签到加人接口
     * @param activitiesId
     * @param idCardNo
     * @return
     */
    @PaddingBaseField
    @Override
    public int addSignIn(Long activitiesId, String idCardNo) {
        //首先判断是否组织下的人
        SysUser sysUser = userMapper.selectUserByIdCardNo(idCardNo);
        if (sysUser==null){
            throw new BusinessDataCheckFailException("身份证不正确");
        }
        //查看活动中已存在的人
        List<TabPbParticipant>list= pbParticipantMapper.selectDetailTableByActivitiesId(activitiesId);
        for (TabPbParticipant tabPbParticipant : list) {
            if (tabPbParticipant.getUserId()==sysUser.getUserId().longValue()){
                throw new BusinessDataCheckFailException("已签到成功");
            }
        }
        //判断该人是否是本组织下面的 如果是则签到并把人存入进去
        TabPbActivities tabPbActivities = activitiesMapper.selectByActivitiesId(activitiesId);
        if (sysUser.getDeptId().longValue()==tabPbActivities.getOrgId()){
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("orgId", sysUser.getDeptId());
            conditions.put("exclusion", "1");
            conditions.put("userTag", "59388,59389"); //年老体弱者、经常外出者
            List<Personnel> personnels = activitiesMapper.selectCandidateMemberListWithConditions(conditions);
            long exists = personnels.stream().filter(personel -> personel.getUserId().longValue() == sysUser.getUserId()).count();
            if (exists > 0){
                //添加入进表
                TabPbParticipant participant = new TabPbParticipant();
                participant.setActivitiesId(activitiesId)
                        .setUserId(sysUser.getUserId().longValue())
                        .setRealName(sysUser.getRealname())
                        .setSigninTime(new Date())
                        .setDelFlag("0")
                        .setEblFlag("1")
                        .setActivitytype(tabPbActivities.getActivitiesType().toString());
                pbParticipantMapper.insertSelective(participant);
            }
                throw new BusinessDataCheckFailException("该用户不符合要求,不能签到");
        }

        return 0;
    }

    /**
     * 社区活动查询
     * @param activitiesDto
     * @param page
     * @return
     */
    @Override
    public List<TabPbActivitiesDto> ActivitiesDtoList(TabPbActivitiesDto activitiesDto, Page page) {
        PageHelper.startPage(page);
        var list = activitiesMapper.ActivitiesDtoList(activitiesDto);
        return list;
    }

}
