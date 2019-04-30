package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.UserLoginDto;
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
    SysUserMapper sysUserMapper;

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


    @Override
    public UserInfoVO getUserInfoVO() {
        UserLoginDto userLoginDto = UserContextHolder.currentUser();
        UserInfoVO sysUser = new UserInfoVO();
        sysUser.setUserId(UserContextHolder.getUserIdLong());
        sysUser.setDeptId(UserContextHolder.getOrgId());
        sysUser.setPhone(userLoginDto.getPhone());
        sysUser.setUsername(userLoginDto.getRealname());
        sysUser.setAvatar(userLoginDto.getAvatar());
        sysUser.setManageDeptId(userLoginDto.getManageDeptId());
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
        return new PageInfo<>(reduceListMapper.historyPartyPage(queryBean));
    }

    @Override
    public List<PersonnelVO> partyIdentityVerification(String username, String idCardNo, String phone, Page page) {
        PageHelper.startPage(page);
        return tabSysUserMapper.partyIdentityVerification(username, idCardNo, phone);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int savePartyInfo(PartyInfoDTO partyInfoDTO) {
        if (!tabSysUserMapper.checkIsExistByIdCard(partyInfoDTO.getParty().getIdCardNo())) {
            partyInfoDTO.getParty().setIdentityType(59423L);
            SysUser sys = BeanUtil.copyPropertiesAndPaddingBaseField(partyInfoDTO.getParty(), SysUser.class, true, false);
            //新增用户信息
            tabSysUserMapper.insertSelective(sys);
            //新增或者删除标签信息
            if (partyInfoDTO.getParty().getUserTags() != null && partyInfoDTO.getParty().getUserTags().size() > 0) {
                this.userTagService.updateUserTagByTagType(BeanUtil.copyListPropertiesAndPaddingBaseField(partyInfoDTO.getParty().getUserTags(), TabPbUserTag.class, true, true));
            }
            int effected = 0;
            if (partyInfoDTO.getEducations() != null && partyInfoDTO.getEducations().size() > 0) {
                //赋值主键
                partyInfoDTO.getEducations().forEach(education -> {
                    education.setUserId(sys.getUserId().longValue());
                });
                effected += tabPbPartyEducationMapper.batchInsert(BeanUtil.copyListPropertiesAndPaddingBaseField(partyInfoDTO.getEducations(), TabPbPartyEducation.class, true, false));
            }
            if (partyInfoDTO.getJobTitles() != null && partyInfoDTO.getJobTitles().size() > 0) {
                //赋值主键
                partyInfoDTO.getJobTitles().forEach(job -> {
                    job.setUserId(sys.getUserId().longValue());
                });
                effected += tabPbPartyJobTitleMapper.batchInsert(BeanUtil.copyListPropertiesAndPaddingBaseField(partyInfoDTO.getJobTitles(), TabPbPartyJobTitle.class, true, false));
            }
            if (partyInfoDTO.getWorks() != null && partyInfoDTO.getWorks().size() > 0) {
                //赋值主键
                partyInfoDTO.getWorks().forEach(work -> {
                    work.setUserId(sys.getUserId().longValue());
                });
                effected += tabPbPartyWorkMapper.batchInsert(BeanUtil.copyListPropertiesAndPaddingBaseField(partyInfoDTO.getWorks(), TabPbPartyWork.class, true, false));
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
            SysUser sys = BeanUtil.copyPropertiesAndPaddingBaseField(partyInfoDTO.getParty(), SysUser.class, true, true);
            effected += tabSysUserMapper.updateByPrimaryKeySelective(sys);
            //新增或者删除标签信息
            if (partyInfoDTO.getParty().getUserTags() != null && partyInfoDTO.getParty().getUserTags().size() > 0) {
                this.userTagService.updateUserTagByTagType(BeanUtil.copyListPropertiesAndPaddingBaseField(partyInfoDTO.getParty().getUserTags(), TabPbUserTag.class, true, true));
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
                    if (partyInfoDTO.getEducations().get(i).getEducationId() == null) {
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
                    effected += tabPbPartyEducationMapper.batchInsert(BeanUtil.copyListPropertiesAndPaddingBaseField(inserts, TabPbPartyEducation.class, true, false));
                }
                //修改处理
                if (updates.size() > 0) {
                    effected += tabPbPartyEducationMapper.batchUpdate(BeanUtil.copyListPropertiesAndPaddingBaseField(updates, TabPbPartyEducation.class, true, true));
                }
                //删除处理
                if (delete != null && delete.size() > 0) {
                    List<TabPbPartyEducation> deleteSource = BeanUtil.copyListPropertiesAndPaddingBaseField(delete, TabPbPartyEducation.class, true, true);
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
                    List<TabPbPartyEducation> deleteSource = BeanUtil.copyListPropertiesAndPaddingBaseField(partyEducationVOS, TabPbPartyEducation.class, true, true);
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
                    if (partyInfoDTO.getJobTitles().get(i).getJobTitleId() == null) {
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
                    effected += tabPbPartyJobTitleMapper.batchInsert(BeanUtil.copyListPropertiesAndPaddingBaseField(inserts, TabPbPartyJobTitle.class, true, false));
                }
                //修改处理
                if (updates.size() > 0) {
                    effected += tabPbPartyJobTitleMapper.batchUpdate(BeanUtil.copyListPropertiesAndPaddingBaseField(updates, TabPbPartyJobTitle.class, true, true));
                }
                //删除处理
                if (delete != null && delete.size() > 0) {
                    List<TabPbPartyJobTitle> deleteSource = BeanUtil.copyListPropertiesAndPaddingBaseField(tabPbPartyJobTitleMapper.findAllByUserId(id), TabPbPartyJobTitle.class, true, true);
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
                    List<TabPbPartyJobTitle> deleteSource = BeanUtil.copyListPropertiesAndPaddingBaseField(partyJobTitleVOs, TabPbPartyJobTitle.class, true, true);
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
                    if (partyInfoDTO.getWorks().get(i).getWorkId() == null) {
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
                    effected += tabPbPartyWorkMapper.batchInsert(BeanUtil.copyListPropertiesAndPaddingBaseField(inserts, TabPbPartyWork.class, true, false));
                }
                //修改处理
                if (updates.size() > 0) {
                    effected += tabPbPartyWorkMapper.batchUpdate(BeanUtil.copyListPropertiesAndPaddingBaseField(updates, TabPbPartyWork.class, true, true));
                }
                //删除处理
                if (delete != null && delete.size() > 0) {
                    List<TabPbPartyWork> deleteSource = BeanUtil.copyListPropertiesAndPaddingBaseField(tabPbPartyWorkMapper.findAllByUserId(id), TabPbPartyWork.class, true, true);
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
                    List<TabPbPartyWork> deleteSource = BeanUtil.copyListPropertiesAndPaddingBaseField(partyWorkVOS, TabPbPartyWork.class, true, true);
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
        if ("14307".equals(deptId) && "2".equals(orgRange)) {
            queryBean.setOrgRange("0");
        }
        PageHelper.startPage(page);
        List<PartyMemberInformationVO> partyMemberInformationVO = tabSysUserMapper.selectPageByMap(queryBean);
        return new PageInfo<>(calculationComplete(partyMemberInformationVO));
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
