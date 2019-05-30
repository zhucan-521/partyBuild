package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.BeanUtil;
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
 * @author liu tang gang
 */
@Service
public class PartyInformationServiceImpl implements PartyInformationService {

    public static final int COMPLETE_SEED = 4;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private TabPbFlowOutMapper tabPbFlowOutMapper;

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private TabPbMemberReduceListMapper reduceListMapper;

    @Autowired
    private TabPbPartyEducationMapper tabPbPartyEducationMapper;

    @Autowired
    private TabPbPartyJobTitleMapper tabPbPartyJobTitleMapper;

    @Autowired
    private TabPbPartyWorkMapper tabPbPartyWorkMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private PartyMembershipServiceImpl partyMembershipServiceImpl;

    //长沙市组织id
    private final String ORG_ID = "14307";

    //当前组织范围
    private final String RANGE_STATE_LEVEL = "0";

    //当前组织包含所有下级
    private final String RANGE_STATE_SUBORDINATE = "2";

    @Override
    public List<PartyMemberChooseVO> selectPartyMemberChooseVOListByQueryBean(PartyMemberChooseQueryBean queryBean, Page page) {
        PageHelper.startPage(page);
        return tabSysUserMapper.selectPartyMemberChooseVOListByQueryBean(queryBean);
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
    public PageInfo<HistoryPartyVO> getPartyHistoryList(HistoricalPartyMemberQueryBean queryBean, Page page) {
        PageHelper.startPage(page);
        //查历史党员
        List<HistoryPartyVO> historyPartyVO = reduceListMapper.selectPartyHistoryList(queryBean);
        PageHelper.startPage(page);
        //用于查询以前历史党员记录
        List<MemberReducesVO> memberReducesVO = reduceListMapper.selectInvalidPartyHistoryList(queryBean);
        //计算党龄
        for (int i = 0; i < historyPartyVO.size(); i++) {
            //理论党龄 (加入党组织时间-当前减少时间 因为偏差问题返回月份)
            Integer age = historyPartyVO.get(i).getPartyStanding();
            if (age != null) {
                //党龄减去以前党员减少的情况
                if (memberReducesVO != null && memberReducesVO.size() > 0) {
                    for (int j = 0; j < memberReducesVO.size(); j++) {
                        if (memberReducesVO.get(j) != null && memberReducesVO.get(j).getUserId() != null) {
                            if (memberReducesVO.get(j).getUserId().equals(historyPartyVO.get(i).getUserId())) {
                                if (memberReducesVO.get(j).getAge() != null) {
                                    //党龄为负数逻辑控制
                                    if (age - memberReducesVO.get(j).getAge() <= 0) {
                                        age = 0;
                                        break;
                                    } else {
                                        age -= memberReducesVO.get(j).getAge();
                                    }
                                }
                            }
                        }
                    }
                    //计算年,未满一年 不计算
                    age = age / 12;
                }
            } else {
                age = 0;
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int savePartyInfo(PartyInfoDTO partyInfoDTO) {
        if (!tabSysUserMapper.checkIsExistByIdCard(partyInfoDTO.getParty().getIdCardNo())) {
            if (!tabSysUserMapper.checkIsExistByPhone(partyInfoDTO.getParty().getPhone())) {
                int effected = 0;
                SysUser sys = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyInfoDTO.getParty(), SysUser.class, false);
                //检测组织Id
                checkIsExist(sys);
                if (partyInfoDTO.getEducations() != null && partyInfoDTO.getEducations().size() > 0) {
                    //取最近的学历信息 排序
                    sortEducationDTO(partyInfoDTO.getEducations(), sys, false);
                }
                if (partyInfoDTO.getJobTitles() != null && partyInfoDTO.getJobTitles().size() > 0) {
                    //维护用户表 技术职务名称
                    sortPartyJobTitleDTO(partyInfoDTO.getJobTitles(), sys, false);
                }
                if (partyInfoDTO.getWorks() != null && partyInfoDTO.getWorks().size() > 0) {
                    //取得最近的 工作信息
                    sortPartyWorkDTO(partyInfoDTO.getWorks(), sys, false);
                }
                //新增
                effected += tabSysUserMapper.insertSelective(sys);
                //新增或者删除标签信息
                if (partyInfoDTO.getParty().getUserTags() != null) {
                    this.userTagService.batchInsertUserTagDTO(partyInfoDTO.getParty().getUserTags());
                }
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
                        job.setUserId(sys.getUserId());
                    });
                    effected += tabPbPartyJobTitleMapper.batchInsert(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyInfoDTO.getJobTitles(), TabPbPartyJobTitle.class, false));
                }
                if (partyInfoDTO.getWorks() != null && partyInfoDTO.getWorks().size() > 0) {
                    //赋值主键
                    partyInfoDTO.getWorks().forEach(work -> {
                        work.setUserId(sys.getUserId());
                    });
                    effected += tabPbPartyWorkMapper.batchInsert(BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(partyInfoDTO.getWorks(), TabPbPartyWork.class, false));
                }
                MembershipDTO membershipDTO = new MembershipDTO();
                //添加一条党籍
                membershipDTO.setUserId(sys.getUserId()).setIdentityType(sys.getIdentityType()).setType(sys.getRegistryStatus());
                effected += partyMembershipServiceImpl.insertMembershipDTO(membershipDTO);
                return effected;
            }
            throw new BusinessDataCheckFailException("该手机号码已存在");
        }
        throw new BusinessDataCheckFailException("该党员已存在");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updatePartyInfo(PartyInfoDTO partyInfoDTO) {
        Long id = partyInfoDTO.getParty().getUserId();
        if (tabSysUserMapper.checkIsExistByUserId(id)) {
            int effected = 0;
            SysUser sys = BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField(partyInfoDTO.getParty(), SysUser.class, true);
            //检测组织Id
            checkIsExist(sys);
            //新增或者删除标签信息
            if (partyInfoDTO.getParty().getUserTags() != null) {
                this.userTagService.batchInsertUserTagDTO(partyInfoDTO.getParty().getUserTags());
            }
            //新增学历信息
            if (partyInfoDTO.getEducations() != null && partyInfoDTO.getEducations().size() > 0) {
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
                //排序+考虑维护用户表的学历,毕业院校,学位字段
                sortEducationDTO(partyInfoDTO.getEducations(), sys, false);
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
                //维护用户表学历字段
                sortEducationDTO(null, sys, true);
            }
            //新增技术信息
            if (partyInfoDTO.getJobTitles() != null && partyInfoDTO.getJobTitles().size() > 0) {
                //分别判断新增或者修改
                List<PartyJobTitleDTO> inserts = new ArrayList<PartyJobTitleDTO>();
                List<PartyJobTitleDTO> updates = new ArrayList<PartyJobTitleDTO>();
                //考虑删除  获取原先所有数据
                List<PartyJobTitleVO> delete = tabPbPartyJobTitleMapper.findAllByUserId(id);
                for (int i = 0; i < partyInfoDTO.getJobTitles().size(); i++) {
                    partyInfoDTO.getJobTitles().get(i).setUserId(id);
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
                    List<TabPbPartyJobTitle> deleteSource = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(delete, TabPbPartyJobTitle.class, true);
                    //设置删除状态
                    for (int i = 0; i < deleteSource.size(); i++) {
                        deleteSource.get(i).setDelFlag(true);
                    }
                    effected += tabPbPartyJobTitleMapper.batchUpdate(deleteSource);
                }
                //排序 维护用户表的技术职务信息,职务资格,离岗时间
                sortPartyJobTitleDTO(partyInfoDTO.getJobTitles(), sys, false);
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
                //维护用户表字段
                sortPartyJobTitleDTO(null, sys, true);
            }
            //新增工作信息
            if (partyInfoDTO.getWorks() != null && partyInfoDTO.getWorks().size() > 0) {
                //分别判断新增或者修改
                List<PartyWorkDTO> inserts = new ArrayList<PartyWorkDTO>();
                List<PartyWorkDTO> updates = new ArrayList<PartyWorkDTO>();
                //考虑删除
                List<PartyWorkVO> delete = tabPbPartyWorkMapper.findAllByUserId(id);
                for (int i = 0; i < partyInfoDTO.getWorks().size(); i++) {
                    partyInfoDTO.getWorks().get(i).setUserId(id);
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
                    List<TabPbPartyWork> deleteSource = BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField(delete, TabPbPartyWork.class, true);
                    //设置删除状态
                    for (int i = 0; i < deleteSource.size(); i++) {
                        deleteSource.get(i).setDelFlag(true);
                    }
                    effected += tabPbPartyWorkMapper.batchUpdate(deleteSource);
                }
                //维护用户表的 新阶段 一线情况
                sortPartyWorkDTO(partyInfoDTO.getWorks(), sys, false);
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
                sortPartyWorkDTO(null, sys, true);
            }
            effected += tabSysUserMapper.updateByPrimaryKeySelectiveSpecialModification(sys);
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
//        PageHelper.startPage(page);
        Long pageSize = page.getPageSize();
        long index = (page.getPageNum() - 1) * pageSize;
        if (index < 0) {
            index = 0L;
        }
        queryBean.setIndex(index);
        queryBean.setLimit(pageSize);
        List<SystemDetailsVO> systemDetailsVO = tabSysUserMapper.selectPageByMap(queryBean);
        int count = tabSysUserMapper.selectPageByMapCOUNT(queryBean);
        List<PartyMemberInformationVO> partyMemberInformationVOS = calculationComplete(systemDetailsVO);
        PageInfo<PartyMemberInformationVO> pageInfo = new PageInfo<>(partyMemberInformationVOS);
        pageInfo.setTotal(count);
        return pageInfo;
    }

    //检查id是否存在
    public void checkIsExist(SysUser user) {
        if (!tabSysDeptMapper.checkIsExistByOrgId(user.getDeptId())) {
            throw new BusinessDataIncompleteException("组织ID不存在");
        }
    }

    //排序学历
    public void sortEducationDTO(List<PartyEducationDTO> partyEducationDTO, SysUser sys, Boolean isDelete) {
        if (!isDelete) {
            partyEducationDTO.sort((o1, o2) -> {
                if (o1.getGraduateTime().after(o2.getGraduateTime())) {
                    return -1;
                } else if (o1.getGraduateTime().before(o2.getGraduateTime())) {
                    return 1;
                }
                return 0;
            });
            sys.setEducation(partyEducationDTO.get(0).getLevel());
            sys.setGraduatedFrom(partyEducationDTO.get(0).getGraduatedSchool());
            sys.setDegree(partyEducationDTO.get(0).getDegree());
            sys.setProfession(partyEducationDTO.get(0).getSpec());
        } else {
            sys.setEducation(null);
            sys.setGraduatedFrom(null);
            sys.setDegree(null);
            sys.setProfession(null);
        }
    }

    //排序工作信息
    public void sortPartyJobTitleDTO(List<PartyJobTitleDTO> partyJobTitleDTO, SysUser sys, Boolean isDelete) {
        if (!isDelete) {
            partyJobTitleDTO.sort((o1, o2) -> {
                if (o1.getAppointEndDate().after(o2.getAppointEndDate())) {
                    return -1;
                } else if (o1.getAppointEndDate().before(o2.getAppointEndDate())) {
                    return 1;
                }
                return 0;
            });
            sys.setTechnician(partyJobTitleDTO.get(0).getPost());
            sys.setPostTime(partyJobTitleDTO.get(0).getAppointEndDate());
        } else {
            sys.setTechnician(null);
            sys.setPostTime(null);
        }
    }

    //排序职业职称
    public void sortPartyWorkDTO(List<PartyWorkDTO> partyWorkDTO, SysUser sys, Boolean isDelete) {
        if (!isDelete) {
            partyWorkDTO.sort((o1, o2) -> {
                if (o1.getEndDate().after(o2.getEndDate())) {
                    return -1;
                } else if (o1.getEndDate().before(o2.getEndDate())) {
                    return 1;
                }
                return 0;
            });
            sys.setStratum(partyWorkDTO.get(0).getStratum());
            sys.setFrontLine(partyWorkDTO.get(0).getFrontLine());
            sys.setJobPosition(partyWorkDTO.get(0).getPost());
        } else {
            sys.setStratum(null);
            sys.setFrontLine(null);
            sys.setJobPosition(null);
        }
    }

    public List<PartyMemberInformationVO> calculationComplete(List<SystemDetailsVO> userList) {
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
            if (ObjectUtils.isEmpty(user.getFixPhone())) {
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
            if (ObjectUtils.isEmpty(user.getPositived())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getPhone())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getEducation())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getIsLlost())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getWorkResumes())) {
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
            if (ObjectUtils.isEmpty(user.getProfession())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getStratum())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getHealth())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(user.getAvatar())) {
                tool -= COMPLETE_SEED;
            }
            user.setComplete(tool);
        });
        return BeanUtil.generateTargetListAndCopyProperties(userList, PartyMemberInformationVO.class);
    }

    @Override
    public PartyMemberChooseVO choosePartyMemberVOByIdCardNo(String idCardNo) {
        return  tabSysUserMapper.selectPartyMemberChooseVOByIdCardNo(idCardNo);
    }
}
