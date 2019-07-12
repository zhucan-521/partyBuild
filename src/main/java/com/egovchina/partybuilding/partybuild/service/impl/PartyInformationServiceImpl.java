package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysUser;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataIncompleteException;
import com.egovchina.partybuilding.common.util.BeanUtil;
import com.egovchina.partybuilding.common.util.CollectionUtil;
import com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil;
import com.egovchina.partybuilding.partybuild.dto.*;
import com.egovchina.partybuilding.partybuild.entity.*;
import com.egovchina.partybuilding.partybuild.repository.*;
import com.egovchina.partybuilding.partybuild.service.PartyInformationService;
import com.egovchina.partybuilding.partybuild.service.UserTagService;
import com.egovchina.partybuilding.partybuild.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    @Autowired
    private TabPbUnitInfoMapper tabPbUnitInfoMapper;

    @Autowired
    ExtendedInfoServiceImpl extendedInfoServiceImpl;

    //长沙市组织id
    private final String ORG_ID = "14307";

    //当前组织范围
    private final String RANGE_STATE_LEVEL = "0";

    //当前组织包含所有下级
    private final String RANGE_STATE_SUBORDINATE = "2";

    @Override
    public PageInfo<PartyMemberChooseVO> selectPartyMemberChooseVOListByQueryBean(PartyMemberChooseQueryBean queryBean, Page page) {
        String orgId = String.valueOf(queryBean.getOrgId());
        String orgRange = String.valueOf(queryBean.getOrgRange()); // 2 包含所有下级
        if (ORG_ID.equals(orgId) && RANGE_STATE_SUBORDINATE.equals(orgRange)) {
            queryBean.setOrgRange(RANGE_STATE_LEVEL);
        }
        Long pageSize = page.getPageSize();
        long index = (page.getPageNum() - 1) * pageSize;
        if (index < 0) {
            index = 0L;
        }
        queryBean.setIndex(index);
        queryBean.setLimit(pageSize);
        List<PartyMemberChooseVO> partyMemberChooseVOS = tabSysUserMapper.selectPartyMemberChooseVOListByQueryBean(queryBean);
        int count = tabSysUserMapper.selectPartyMemberChooseVOListCountByQueryBean(queryBean);
        PageInfo<PartyMemberChooseVO> pageInfo = new PageInfo<>(partyMemberChooseVOS);
        pageInfo.setTotal(count);
        return pageInfo;
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
        return new PageInfo<>(historyPartyVO);
    }

    @Override
    public PageInfo<PersonnelVO> partyIdentityVerification(String queryValue, Page page) {
        PageHelper.startPage(page);
        return new PageInfo<>(tabSysUserMapper.partyIdentityVerification(queryValue));
    }

    @Override
    public List<PartyWorkVO> getParyWorkVO(Long userId) {
        return tabPbPartyWorkMapper.findAllByUserId(userId);
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
                //添加一条党籍
                Long[] type = new Long[3];
                //type 0党籍处理 1党籍状态
                type[0] = transform(sys.getRegistryStatus());
                type[1] = sys.getRegistryStatus();
                updatePartyMembership(sys.getUserId(), type, new Date());
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
            //判断党籍是否发生改变
            List<MembershipVO> membershipVOListByCondition = partyMembershipServiceImpl.getMembershipVOListByCondition(sys.getUserId(), new Page());
            if (membershipVOListByCondition != null && membershipVOListByCondition.size() > 0) {
                //改变就新增党籍信息
                if (!membershipVOListByCondition.get(0).getType().equals(transform(sys.getRegistryStatus()))) {
                    //添加一条党籍
                    Long[] type = new Long[3];
                    type[0] = transform(sys.getRegistryStatus());
                    type[1] = sys.getRegistryStatus();
                    updatePartyMembership(sys.getUserId(), type, new Date());
                }
            } else {
                //不考虑membershipVOListByCondition为null,不存在null
                if (sys.getRegistryStatus() != null) {
                    //数据库党籍没记录,直接新增党籍信息
                    Long[] type = new Long[3];
                    type[0] = transform(sys.getRegistryStatus());
                    type[1] = sys.getRegistryStatus();
                    updatePartyMembership(sys.getUserId(), type, new Date());
                }
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
        HashMap<String, Object> queryMap = new HashMap<String, Object>();
        if (queryBean.getEducation() != null) {
            queryMap.put("education", queryBean.getEducation().split(","));
        }
        if (queryBean.getNation() != null) {
            queryMap.put("nation", queryBean.getNation().split(","));
        }
        if (queryBean.getUnitProperty() != null) {
            queryMap.put("unitProperty", queryBean.getUnitProperty().split(","));
        }
        if (queryBean.getGender() != null) {
            queryMap.put("gender", queryBean.getGender().split(","));
        }
        if (queryBean.getTagTypes() != null) {
            queryMap.put("tagTypes", queryBean.getTagTypes().split(","));
        }
        queryMap.put("deptId", queryBean.getDeptId());
        queryMap.put("username", queryBean.getUsername());
        queryMap.put("identityType", queryBean.getIdentityType());
        queryMap.put("idCardNo", queryBean.getIdCardNo());
        queryMap.put("joinTimeBegin", queryBean.getJoinTimeBegin());
        queryMap.put("joinTimeEnd", queryBean.getJoinTimeEnd());
        queryMap.put("ageBegin", queryBean.getAgeBegin());
        queryMap.put("ageEnd", queryBean.getAgeEnd());
        queryMap.put("index", index);
        queryMap.put("limit", pageSize);
        queryMap.put("orgRange", queryBean.getOrgRange());
        List<SystemDetailsVO> systemDetailsVO = tabSysUserMapper.selectPageByMap(queryMap);
        int count = tabSysUserMapper.selectPageByMapCOUNT(queryMap);
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
        return tabSysUserMapper.selectPartyMemberChooseVOByIdCardNo(idCardNo);
    }

    @Override
    public List<HistoryInformationGraphVO> getHistoryInformationGraph(Page page, Boolean orgnizeLife, Boolean communityActivity, Boolean partyMemberComment, Long userId) {
        PageHelper.startPage(page);
        return tabPbPartyWorkMapper.selectHistoryInformationGraphByBasicAndOrgnizeLifeWithCommunityActivity(orgnizeLife, communityActivity, partyMemberComment, userId);
    }

    @Override
    public void excelImportEffectiveDataToDb(List<SysUser> effectiveList) {
        if (CollectionUtil.isNotEmpty(effectiveList)) {
            tabSysUserMapper.batchInsert(effectiveList);
        }
    }

    //此方法将excel中的数据转换成要操作的业务实体
    @Override
    public SysUser excelImportEntityConvert(String[] row) {
        int index = 0;
        SysUser sysUser = new SysUser();
        String realname = row[++index];//姓名
        String identityType = row[++index];//党员类别
        String deptId = row[++index];//所在组织
        String sex = row[++index];//性别
        String age = row[++index];//出生年月
        String nation = row[++index];//民族
        String phone = row[++index];//联系电话
        String filesManageUnitId = row[++index];//档案管理单位
        String registryStatus = row[++index];//党籍状态
        String joinOrgTime = row[++index];//加入党组织日期
        String idCardNo = row[++index];//身份证
        String joinTime = row[++index];//入党时间
        try {
            sysUser.setRealname(realname).setIdentityType(Long.parseLong(identityType.split("_")[0])).setDeptId(Long.parseLong(deptId))
                    .setGender(Long.parseLong(sex.split("_")[0])).setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(age)).setNation(Long.parseLong(nation.split("_")[0])).setPhone(phone)
                    .setFilesManageUnitId(Long.parseLong(filesManageUnitId)).setRegistryStatus(Long.parseLong(registryStatus.split("_")[0]))
                    .setJoinOrgTime(new SimpleDateFormat("yyyy-MM-dd").parse(joinOrgTime)).setIdCardNo(idCardNo).setJoinTime(new SimpleDateFormat("yyyy-MM-dd").parse(joinTime));
        } catch (ParseException e) {

        }
        PaddingBaseFieldUtil.paddingBaseFiled(sysUser);
        return sysUser;
    }

    @Override
    public String excelTemplateName() {
        return "partyInfo";
    }

    @Override
    public String excelImportPreValidate(List<SysUser> effectiveList, String[] row) {
        boolean isExistIdCardNo = false;
        boolean isExistPhone = false;
        int index = 0;
        String realname = row[++index];//姓名
        String identityType = row[++index];//党员类别
        String deptId = row[++index];//所在组织
        String sex = row[++index];//性别
        String age = row[++index];//出生年月
        String nation = row[++index];//民族
        String phone = row[++index];//联系电话
        String filesManageUnitId = row[++index];//档案管理单位
        String registryStatus = row[++index];//党籍状态
        String joinOrgTime = row[++index];//加入党组织日期
        String idCardNo = row[++index];//身份证
        String joinTime = row[++index];//入党时间
        StringBuffer error = new StringBuffer();

        if (StringUtils.isEmpty(realname)) {
            error.append("姓名不能为空 | ");
        }
        if (StringUtils.isEmpty(identityType)) {
            error.append("人员类别不能为空 | ");
        }
        if (StringUtils.isEmpty(deptId)) {
            error.append("组织id不能为空 | ");
        }
        Long reDeptId = null;
        try {
            reDeptId = tabSysDeptMapper.selectIdByName(deptId);
        } catch (Exception e) {
            error.append("该组织名称存在多个,数据异常 | ");
        }
        if (reDeptId == null) {
            error.append("该组织名称无效 | ");
        }

        if (StringUtils.isEmpty(sex)) {
            error.append("性别不能为空 | ");
        }
        if (StringUtils.isEmpty(age)) {
            error.append("出生年月不能为空 | ");
        }
        if (StringUtils.isEmpty(nation)) {
            error.append("民族不能为空 | ");
        }
        if (StringUtils.isEmpty(phone)) {
            error.append("联系电话不能为空 | ");
        }
        if (tabSysUserMapper.checkIsExistByPhone(phone)) {

            error.append("联系电话已经存在 | ");
        }
        if (StringUtils.isEmpty(filesManageUnitId)) {
            error.append("档案管理单位不能为空 |");
        }
        Long unit = null;
        try {
            unit = tabPbUnitInfoMapper.selectUnitIdByUnitName(filesManageUnitId);
        } catch (Exception e) {
            error.append("该单位名称存在多个,数据异常");
        }
        if (unit == null) {
            error.append("该单位名称无效");
        }
        if (StringUtils.isEmpty(registryStatus)) {
            error.append("党籍状态不能为空 | ");
        }
        if (StringUtils.isEmpty(joinOrgTime)) {
            error.append("加入党组织时间不能为空 | ");
        }
        if (StringUtils.isEmpty(idCardNo)) {
            error.append("身份证号码不能为空 | ");
        }
        if (tabSysUserMapper.checkIsExistByIdCard(idCardNo)) {
            isExistIdCardNo = true;
            error.append("身份证号码已经重复 | ");
        }
        if (StringUtils.isEmpty(joinTime)) {
            error.append("入党时间不能为空 | ");
        }
        if (identityType != null) {
            try {
                Long.parseLong(identityType.split("_")[0]);
            } catch (Exception e) {
                error.append("党员类别数据转换异常");
            }
        }
        if (sex != null) {
            try {
                Long.parseLong(sex.split("_")[0]);
            } catch (Exception e) {
                error.append("性别数据转换异常");
            }
        }
        if (age != null) {
            try {
                new SimpleDateFormat("yyyy-MM-dd").parse(age);
            } catch (Exception e) {
                error.append("出生日期数据转换异常");
            }
        }
        if (nation != null) {
            try {
                Long.parseLong(nation.split("_")[0]);
            } catch (Exception e) {
                error.append("民族数据转换异常");
            }
        }

        if (registryStatus != null) {
            try {
                Long.parseLong(registryStatus.split("_")[0]);
            } catch (Exception e) {
                error.append("党籍状态数据转换异常");
            }
        }
        if (joinOrgTime != null) {
            try {
                new SimpleDateFormat("yyyy-MM-dd").parse(joinOrgTime);
            } catch (Exception e) {
                error.append("加入党组织时间数据转换异常");
            }
        }
        if (joinTime != null) {
            try {
                new SimpleDateFormat("yyyy-MM-dd").parse(joinTime);
            } catch (Exception e) {
                error.append("入党时间数据转换异常");
            }

        }
        //导入数据身份证重复问题,身份证排除非空重复判断
        if (!StringUtils.isEmpty(idCardNo)) {
            //排除数据库重复情况 + 第一条数据
            if (CollectionUtil.isNotEmpty(effectiveList) && !isExistIdCardNo) {
                //用于判断提交的数据排重，每次只判断前面
                for (int i = 0; i < effectiveList.size(); i++) {
                    if (idCardNo.equals(effectiveList.get(i).getIdCardNo())) {
                        error.append("导入数据中存在身份证号码重复 | ");
                    }
                }
            }
        }
        //手机号码雷同
        if (!StringUtils.isEmpty(phone)) {
            //排除数据库重复情况
            if (CollectionUtil.isNotEmpty(effectiveList) && !isExistPhone) {
                //用于判断提交的数据排重，每次只判断前面
                for (int i = 0; i < effectiveList.size(); i++) {
                    if (phone.equals(effectiveList.get(i).getPhone())) {
                        error.append("导入数据中存在电话重复 | ");
                    }
                }
            }
        }
        if (reDeptId != null) {
            //无错误
            if (error.length() <= 0) {
                //改变excel的值
                row[3] = reDeptId.toString();
            }

        }
        if (unit != null) {
            if (error.length() <= 0) {
                //改变excel的值
                row[8] = unit.toString();
            }
        }
        return error.toString();
    }

    /**
     * 党籍状态码值转化为党籍处理码值
     *
     * @param status
     * @return
     */
    public static Long transform(Long status) {
        Long partyProcessing = null;
        if (status == null) {
            throw new BusinessDataCheckFailException("党籍状态不能为空");
        }
        //党籍为正式或预备  党籍处理为登记 其他参照码值DJZT DJCL进行对比
        if (status == 59325L || status == 59326L) {
            partyProcessing = 59584L;
        } else if (status == 59327L) {
            partyProcessing = 59581L;
        } else if (status == 59328L) {
            partyProcessing = 59583L;
        } else if (status == 59329L) {
            partyProcessing = 59582L;
        } else if (status == 59585L) {
            partyProcessing = 59579L;
        }
        return partyProcessing;
    }

    /**
     * @param userId 用户id
     * @param type   党籍处理
     */
    public void updatePartyMembership(Long userId, Long[] type, Date date) {
        //查询identity_type
        Long identityType = tabSysUserMapper.selectUserByIdFindIdentity(userId);
        MembershipDTO membershipDTO = new MembershipDTO();
        //Type设置党籍状态,理由  处理时间
        membershipDTO.setUserId(userId).setIdentityType(identityType).setType(type[1]).setReason(getReason(type)).setMembershipTime(date);
        //新增党籍
        partyMembershipServiceImpl.insertMembershipDTO(membershipDTO);
    }

    /**
     * 进行拼接党籍记录理由
     *
     * @param type
     * @return
     */
    public String getReason(Long[] type) {
        StringBuffer reason = new StringBuffer();
        //获取党籍处理
        Long reductionMethod = type[0];
        //获取出党方式
        Long wayOut = type[2];
        if (reductionMethod != null) {
            reason.append(reduceListMapper.selectDictName(type[0]));
        }
        if (wayOut != null) {
            reason.append(reduceListMapper.selectDictName(type[2]));
        }
        return reason.toString();
    }
}
