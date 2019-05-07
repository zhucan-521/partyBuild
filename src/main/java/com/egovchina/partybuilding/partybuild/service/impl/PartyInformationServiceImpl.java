package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.Profile;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.*;
import com.egovchina.partybuilding.partybuild.entity.*;
import com.egovchina.partybuilding.partybuild.repository.*;
import com.egovchina.partybuilding.partybuild.service.PartyInformationService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhucan
 */
@Service
public class PartyInformationServiceImpl implements PartyInformationService {

    public static final int COMPLETE_SEED = 4;

    @Autowired
    TabSysUserMapper tabSysUserMapper;

    @Autowired
    TabPbFlowOutMapper tabPbFlowOutMapper;

    @Autowired
    TabPbFlowInMapper tabPbFlowInMapper;

    @Autowired
    UserTagService userTagService;

    @Autowired
    private TabPbMemberReduceListMapper reduceListMapper;

    @Autowired
    TabPbPositivesMapper positivesMapper;

    @Autowired
    private TabPbPartyEducationMapper tabPbPartyEducationMapper;

    @Autowired
    private TabPbPartyJobTitleMapper tabPbPartyJobTitleMapper;

    @Autowired
    private TabPbPartyWorkMapper tabPbPartyWorkMapper;

    @Autowired
    private TabPbAbroadMapper tabPbAbroadMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    //长沙市组织id
    private final String ORG_ID = "14307";

    //当前组织范围
    private final String RANGE_STATE_LEVEL = "0";

    //当前组织包含所有下级
    private final String RANGE_STATE_SUBORDINATE = "2";

    //非党员
    private final Long TYPE_STATE = 59423L;

    @Override
    public UserInfoVO getUserInfoVO() {
        Profile profile = UserContextHolder.currentUser();
        UserInfoVO sysUser = new UserInfoVO();
        sysUser.setUserId(profile.getUserId());
        sysUser.setDeptId(profile.getDeptId());
        sysUser.setPhone(profile.getPhone());
        sysUser.setUsername(profile.getRealname());
        sysUser.setManageDeptId(profile.getManageDeptId());
        return sysUser;
    }

    /**
     * 根据社区名字模糊查找社区
     *
     * @param
     * @return
     */
    @Override
    public List<CommunityVO> selectCommunityVO(CommunityDTO communityDTO) {
        return tabPbFlowOutMapper.selectCommuntiyDto(communityDTO);
    }

    @Override
    public PageInfo<HistoryPartyVO> historyPartyPage(HistoricalPartyMemberQueryBean queryBean, Page page) {
        PageHelper.startPage(page);
        List<HistoryPartyVO> historyPartyVO = reduceListMapper.historyPartyPage(queryBean);
        //获取党员出国记录
        List<MemberReducesVO> memberReducesVO = tabPbAbroadMapper.findAbroadDetailsByPartyId(queryBean);
        //计算党龄
        for (int i = 0; i < historyPartyVO.size(); i++) {
            //理论党龄
            Integer age = historyPartyVO.get(i).getPartyStanding();
            if (age != null) {
                //党龄减去出国时间
                if (memberReducesVO != null && memberReducesVO.size() > 0) {
                    for (int j = 0; j < memberReducesVO.size(); j++) {
                        if (memberReducesVO.get(j) != null && memberReducesVO.get(j).getUserId() != null) {
                            if (memberReducesVO.get(j).getUserId().equals(historyPartyVO.get(i).getUserId())) {
                                if (age <= 0) {
                                    age = 0;
                                    break;
                                }
                                if (memberReducesVO.get(j).getAge() != null) {
                                    age -= memberReducesVO.get(j).getAge();
                                }
                            }
                        }
                    }
                }
            }
            //设置真实党龄
            historyPartyVO.get(i).setPartyStanding(age);
        }
        return new PageInfo<>(historyPartyVO);
    }

    @Override
    public PageInfo<PersonnelVO> partyIdentityVerification(String username, String idCardNo, String phone, Page page) {
        if (StringUtils.isAllBlank(username, idCardNo, phone)) {
            throw new BusinessDataIncompleteException("查询条件不能为空");
        }
        PageHelper.startPage(page);
        return new PageInfo<>(tabSysUserMapper.partyIdentityVerification(username, idCardNo, phone));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int savePartyInfo(PartyInfoDTO partyInfoDTO) {
        if (!tabSysUserMapper.checkIsExistByIdCard(partyInfoDTO.getParty().getIdCardNo())) {
            //补录成非党员
            partyInfoDTO.getParty().setIdentityType(TYPE_STATE);
            SysUser sys = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyInfoDTO.getParty(), SysUser.class, false);
            //检测组织Id
            checkIsExist(sys);
            //新增用户信息
            tabSysUserMapper.insertSelective(sys);
            //新增或者删除标签信息
            if (partyInfoDTO.getParty().getUserTags() != null && partyInfoDTO.getParty().getUserTags().size() > 0) {
                this.userTagService.updateUserTagByTagType(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyInfoDTO.getParty().getUserTags(), TabPbUserTag.class, true));
            }
            int effected = 0;
            if (partyInfoDTO.getEducations() != null && partyInfoDTO.getEducations().size() > 0) {
                //赋值主键
                partyInfoDTO.getEducations().forEach(education -> {
                    education.setUserId(sys.getUserId());
                });
                effected += tabPbPartyEducationMapper.batchInsert(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyInfoDTO.getEducations(), TabPbPartyEducation.class, false));
            }
            if (partyInfoDTO.getJobTitles() != null && partyInfoDTO.getJobTitles().size() > 0) {
                //赋值主键
                partyInfoDTO.getJobTitles().forEach(job -> {
                    job.setUserId(sys.getUserId().longValue());
                });
                effected += tabPbPartyJobTitleMapper.batchInsert(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyInfoDTO.getJobTitles(), TabPbPartyJobTitle.class, false));
            }
            if (partyInfoDTO.getWorks() != null && partyInfoDTO.getWorks().size() > 0) {
                //赋值主键
                partyInfoDTO.getWorks().forEach(work -> {
                    work.setUserId(sys.getUserId().longValue());
                });
                effected += tabPbPartyWorkMapper.batchInsert(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyInfoDTO.getWorks(), TabPbPartyWork.class, false));
            }

            return effected;
        }
        throw new BusinessDataCheckFailException("该党员已存在");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePartyInfo(PartyInfoDTO partyInfoDTO) {
        Long id = partyInfoDTO.getParty().getUserId();
        if (tabSysUserMapper.checkIsExistByUserId(id)) {
            int effected = 0;
            SysUser sys = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyInfoDTO.getParty(), SysUser.class, true);
            //检测组织Id
            checkIsExist(sys);
            effected += tabSysUserMapper.updateByPrimaryKeySelective(sys);
            //新增或者删除标签信息
            if (partyInfoDTO.getParty().getUserTags() != null && partyInfoDTO.getParty().getUserTags().size() > 0) {
                this.userTagService.updateUserTagByTagType(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyInfoDTO.getParty().getUserTags(), TabPbUserTag.class, true));
            }

            //新增学历信息
            if (partyInfoDTO.getEducations() != null) {
                //分别判断新增或者修改
                List<PartyEducationDTO> inserts = new ArrayList<PartyEducationDTO>();
                List<PartyEducationDTO> updates = new ArrayList<PartyEducationDTO>();
                //考虑删除  获取原先所有数据
                List<PartyEducationVO> delete = tabPbPartyEducationMapper.findAllByUserId(id);
                for (int i = 0; i < partyInfoDTO.getEducations().size(); i++) {
                    partyInfoDTO.getEducations().get(i).setUserId(id);
                    //若为空或者0新增
                    if (partyInfoDTO.getEducations().get(i).getEducationId() == null || partyInfoDTO.getEducations().get(i).getEducationId() == 0) {
                        inserts.add(partyInfoDTO.getEducations().get(i));
                    } else {
                        //储存修改数据
                        updates.add(partyInfoDTO.getEducations().get(i));
                        if (delete != null && delete.size() > 0) {
                            //剔除不用删除的
                            for (int j = 0; j < delete.size(); j++) {
                                if (delete.get(j).getEducationId().equals(partyInfoDTO.getEducations().get(i).getEducationId())) {
                                    delete.remove(delete.get(j));
                                    break;
                                }
                            }

                        }
                    }
                }
                //新增处理
                if (inserts.size() > 0) {
                    effected += tabPbPartyEducationMapper.batchInsert(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(inserts, TabPbPartyEducation.class, false));
                }
                //修改处理
                if (updates.size() > 0) {
                    effected += tabPbPartyEducationMapper.batchUpdate(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(updates, TabPbPartyEducation.class, true));
                }
                //删除处理
                if (delete != null && delete.size() > 0) {
                    List<TabPbPartyEducation> deleteSource = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(delete, TabPbPartyEducation.class, true);
                    //设置删除状态
                    for (int i = 0; i < deleteSource.size(); i++) {
                        deleteSource.get(i).setDelFlag(true);
                    }
                    effected += tabPbPartyEducationMapper.batchUpdate(deleteSource);
                }
            } else {
                //非空判断
                List<PartyEducationVO> partyEducationVOS = tabPbPartyEducationMapper.findAllByUserId(id);
                if (partyEducationVOS != null && partyEducationVOS.size() > 0) {
                    //没有直接删除
                    List<TabPbPartyEducation> deleteSource = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyEducationVOS, TabPbPartyEducation.class, true);
                    //设置删除状态
                    for (int i = 0; i < deleteSource.size(); i++) {
                        deleteSource.get(i).setDelFlag(true);
                    }
                    effected += tabPbPartyEducationMapper.batchUpdate(deleteSource);
                }
            }
            //新增技术信息
            if (partyInfoDTO.getJobTitles() != null) {
                //分别判断新增或者修改
                List<PartyJobTitleDTO> inserts = new ArrayList<PartyJobTitleDTO>();
                List<PartyJobTitleDTO> updates = new ArrayList<PartyJobTitleDTO>();
                //考虑删除  获取原先所有数据
                List<PartyJobTitleVO> delete = tabPbPartyJobTitleMapper.findAllByUserId(id);
                for (int i = 0; i < partyInfoDTO.getJobTitles().size(); i++) {
                    partyInfoDTO.getJobTitles().get(i).setUserId(id.longValue());
                    //若为空或者0新增
                    if (partyInfoDTO.getJobTitles().get(i).getJobTitleId() == null || partyInfoDTO.getJobTitles().get(i).getJobTitleId() == 0) {
                        inserts.add(partyInfoDTO.getJobTitles().get(i));
                    } else {
                        updates.add(partyInfoDTO.getJobTitles().get(i));
                        if (delete != null && delete.size() > 0) {
                            //剔除不用删除的
                            for (int j = 0; j < delete.size(); j++) {
                                if (partyInfoDTO.getJobTitles().get(i).getJobTitleId().equals(delete.get(j).getJobTitleId())) {
                                    delete.remove(delete.get(j));
                                    break;
                                }
                            }

                        }
                    }
                }
                //新增处理
                if (inserts.size() > 0) {
                    effected += tabPbPartyJobTitleMapper.batchInsert(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(inserts, TabPbPartyJobTitle.class, false));
                }
                //修改处理
                if (updates.size() > 0) {
                    effected += tabPbPartyJobTitleMapper.batchUpdate(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(updates, TabPbPartyJobTitle.class, true));
                }
                //删除处理
                if (delete != null && delete.size() > 0) {
                    List<TabPbPartyJobTitle> deleteSource = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(tabPbPartyJobTitleMapper.findAllByUserId(id), TabPbPartyJobTitle.class, true);
                    //设置删除状态
                    for (int i = 0; i < deleteSource.size(); i++) {
                        deleteSource.get(i).setDelFlag(true);
                    }
                    effected += tabPbPartyJobTitleMapper.batchUpdate(deleteSource);
                }
            } else {
                List<PartyJobTitleVO> partyJobTitleVOs = tabPbPartyJobTitleMapper.findAllByUserId(id);
                if (partyJobTitleVOs != null && partyJobTitleVOs.size() > 0) {
                    //没有直接删除
                    List<TabPbPartyJobTitle> deleteSource = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyJobTitleVOs, TabPbPartyJobTitle.class, true);
                    //设置删除状态
                    for (int i = 0; i < deleteSource.size(); i++) {
                        deleteSource.get(i).setDelFlag(true);
                    }
                    effected += tabPbPartyJobTitleMapper.batchUpdate(deleteSource);
                }

            }
            //新增工作信息
            if (partyInfoDTO.getWorks() != null) {
                //分别判断新增或者修改
                List<PartyWorkDTO> inserts = new ArrayList<PartyWorkDTO>();
                List<PartyWorkDTO> updates = new ArrayList<PartyWorkDTO>();
                //考虑删除
                List<PartyWorkVO> delete = tabPbPartyWorkMapper.findAllByUserId(id);
                for (int i = 0; i < partyInfoDTO.getWorks().size(); i++) {
                    partyInfoDTO.getWorks().get(i).setUserId(id.longValue());
                    if (partyInfoDTO.getWorks().get(i).getWorkId() == null || partyInfoDTO.getWorks().get(i).getWorkId() == 0) {
                        //补充到新增
                        inserts.add(partyInfoDTO.getWorks().get(i));
                    } else {
                        //补充到修改
                        updates.add(partyInfoDTO.getWorks().get(i));
                        //剔除不用删除的
                        for (int j = 0; j < delete.size(); j++) {
                            if (delete.get(j).getWorkId().equals(partyInfoDTO.getWorks().get(i).getWorkId())) {
                                delete.remove(delete.get(j));
                                break;
                            }
                        }
                    }
                }
                //新增处理
                if (inserts.size() > 0) {
                    effected += tabPbPartyWorkMapper.batchInsert(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(inserts, TabPbPartyWork.class, false));
                }
                //修改处理
                if (updates.size() > 0) {
                    effected += tabPbPartyWorkMapper.batchUpdate(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(updates, TabPbPartyWork.class, true));
                }
                //删除处理
                if (delete != null && delete.size() > 0) {
                    List<TabPbPartyWork> deleteSource = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(tabPbPartyWorkMapper.findAllByUserId(id), TabPbPartyWork.class, true);
                    //设置删除状态
                    for (int i = 0; i < deleteSource.size(); i++) {
                        deleteSource.get(i).setDelFlag(true);
                    }
                    effected += tabPbPartyWorkMapper.batchUpdate(deleteSource);
                }
            } else {
                List<PartyWorkVO> partyWorkVOS = tabPbPartyWorkMapper.findAllByUserId(id);
                if (partyWorkVOS != null && partyWorkVOS.size() > 0) {
                    //没有直接删除
                    List<TabPbPartyWork> deleteSource = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyWorkVOS, TabPbPartyWork.class, true);
                    //设置删除状态
                    for (int i = 0; i < deleteSource.size(); i++) {
                        deleteSource.get(i).setDelFlag(true);
                    }
                    effected += tabPbPartyWorkMapper.batchUpdate(deleteSource);
                }
            }
            return effected;
        }
        throw new BusinessDataIncompleteException("用户ID不存在");
    }
    @Override
    public PageInfo<PartyMemberInformationVO> getPartyList(SysUserQueryBean queryBean, Page page) {
        String deptId = String.valueOf(queryBean.getDeptId());
        String orgRange = String.valueOf(queryBean.getOrgRange()); // 2 包含所有下级
        if (ORG_ID.equals(deptId) && RANGE_STATE_SUBORDINATE.equals(orgRange)) {
            queryBean.setOrgRange(RANGE_STATE_LEVEL);
        }
        PageHelper.startPage(page);
        List<PartyMemberInformationVO> partyMemberInformationVO = tabSysUserMapper.selectPageByMap(queryBean);
        return new PageInfo<>(calculationComplete(partyMemberInformationVO));
    }

    //检查id是否存在
    public void checkIsExist(SysUser user) {
        if (!tabSysDeptMapper.checkIsExistByOrgId(user.getDeptId())) {
            throw new BusinessDataIncompleteException("组织ID不存在");
        }
    }

    public List<PartyMemberInformationVO> calculationComplete(List<PartyMemberInformationVO> userList) {
        userList.forEach(user -> {
            int tool = 100;
            if (ObjectUtils.isEmpty(user.getGender())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getIdCardNo())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getEducation())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getNation())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getIdentityType())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getDeptId())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getJoinTime())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getJoinOrgTime())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getFamilyAddress())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getRegularTime())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getJobPosition())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getCommunityAddr())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getPhone())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getFamilyAddress())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getIsLlost())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getRegistryStatus())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getAncestorPlace())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getIsTaiwaner())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getBirthday())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getMaritalStatus())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getWorkDate())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getFrontLine())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getStratum())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getMigrant())) {
                tool -= COMPLETE_SEED;
            }
            user.setComplete(tool);
        });
        return userList;
    }

}
