package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysDept;
import com.egovchina.partybuilding.common.enums.SystemConfigurationEnum;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.exception.BusinessDataNotFoundException;
import com.egovchina.partybuilding.common.util.*;
import com.egovchina.partybuilding.partybuild.dto.OrganizationDTO;
import com.egovchina.partybuilding.partybuild.dto.OrganizationPositionDTO;
import com.egovchina.partybuilding.partybuild.dto.UnitInfoDTO;
import com.egovchina.partybuilding.partybuild.entity.OrganizationQueryBean;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgClassify;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.entity.TabPbUnitInfo;
import com.egovchina.partybuilding.partybuild.feign.SysConfigFeignClient;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgClassifyMapper;
import com.egovchina.partybuilding.partybuild.repository.TabPbUnitInfoMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.OrgChangeService;
import com.egovchina.partybuilding.partybuild.service.OrgTagService;
import com.egovchina.partybuilding.partybuild.service.OrganizationService;
import com.egovchina.partybuilding.partybuild.vo.ContainsStatisticsOrganizationVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationPartyBuildingWorkVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationPositionVO;
import com.egovchina.partybuilding.partybuild.vo.OrganizationVO;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.BeanUtil.generateTargetListCopyPropertiesAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;
import static com.egovchina.partybuilding.common.util.RedisKeyConstant.ORGANIZATION_COUNT_PARTY_MAN;
import static com.egovchina.partybuilding.common.util.RedisKeyConstant.ORGANIZATION_LIST_FOR_PARENT;

/**
 * 组织信息service实现
 *
 * @Author Zhang Fan
 **/
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

    private static final int COMPLETE_SEED = 10;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private TabPbUnitInfoMapper tabPbUnitInfoMapper;

    @Autowired
    private TabPbOrgClassifyMapper tabPbOrgClassifyMapper;

    @Autowired
    private OrgChangeService orgChangeService;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Autowired
    private OrgTagService orgTagService;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private SysConfigFeignClient sysConfigFeignClient;

    @Override
    public List<OrganizationVO> selectOrganizationVOWithCondition(OrganizationQueryBean queryBean, Page page) {
        PageHelper.startPage(page);
        if (queryBean.getDomainCategory() != null) {
            queryBean.setDomainCategorys(Arrays.asList(queryBean.getDomainCategory().split(",")));
        }
        ReturnEntity returnEntity = sysConfigFeignClient.getConfigurationValue(SystemConfigurationEnum.SHOW_PARTY_GROUP.getItemId());
        if (returnEntity.unOkResp()) {
            throw returnEntity.exception();
        }
        queryBean.setShowPartyGroup(CommonConstant.STATUS_EBL.equals(Optional.ofNullable(returnEntity.getResultObj()).orElse(CommonConstant.STATUS_NOEBL)));
        List<OrganizationVO> list = tabSysDeptMapper.selectOrganizationVOWithCondition(queryBean);
        return calculationComplete(list);
    }

    @Override
    public Boolean checkOrgNameAvailability(String orgName, Long orgId) {
        return tabSysDeptMapper.checkOrgNameAvailability(orgName, orgId);
    }

    @Override
    public Boolean checkOrgCodeAvailability(String orgCode, Long deptId) {
        return tabSysDeptMapper.checkOrgCodeAvailability(orgCode, deptId);
    }

    @Override
    public SysDept selectByPrimaryKey(Long deptId) {
        return tabSysDeptMapper.selectByPrimaryKey(deptId);
    }

    @CacheEvict(value = ORGANIZATION_LIST_FOR_PARENT, allEntries = true)
    @Transactional
    @Override
    public int insertOrganization(OrganizationDTO organizationDTO) {
        int judgment = 0;

        //新增组织信息
        SysDept sysDept = generateTargetCopyPropertiesAndPaddingBaseField(organizationDTO, SysDept.class, false);
        judgment += tabSysDeptMapper.insertSelective(sysDept);
        organizationDTO.setDeptId(sysDept.getDeptId());
        //新增调整【新建】状态
        initToOrganizationChangeTable(organizationDTO);

        //新增分类定等数据，如果需要
        insertClassifyIfNecessary(organizationDTO);
        //新增单位数据
        if (organizationDTO.getUnits() != null) {
            List<TabPbUnitInfo> tabPbUnitInfoList =
                    generateTargetListCopyPropertiesAndPaddingBaseField(organizationDTO.getUnits(), TabPbUnitInfo.class, false);
            if (tabPbUnitInfoList != null && tabPbUnitInfoList.size() > 0) {
                tabPbUnitInfoList.forEach(tabPbUnitInfo -> insertUnitInfo(organizationDTO, tabPbUnitInfo));
            }
        }
        //维护组织full_path、内部编码及子级的（如有必要）
        modifyFullPathInnerCodeAndSubDeptIfNecessary(sysDept);
        tabSysDeptMapper.updateByPrimaryKeySelective(sysDept);
        //维护上级是否为父组织
        judgment += maintainOrgIsParentOrg(sysDept);
        return judgment;
    }

    /**
     * 如有需要，新增分类定等数据
     *
     * @param organizationDTO
     */
    private void insertClassifyIfNecessary(OrganizationDTO organizationDTO) {
        if (organizationDTO.getOrgLevel() != null) {
            TabPbOrgClassify tabPbOrgClassify = new TabPbOrgClassify();
            tabPbOrgClassify.setOrgLevel(organizationDTO.getOrgLevel());
            tabPbOrgClassify.setLevelDate(organizationDTO.getLevelDate());
            tabPbOrgClassify.setDeptId(organizationDTO.getDeptId());
            tabPbOrgClassifyMapper.insertSelective(tabPbOrgClassify);
        }
    }

    /**
     * 初始化组织调整表
     *
     * @param organizationDTO 组织实体
     */
    private void initToOrganizationChangeTable(OrganizationDTO organizationDTO) {
        long orgId = organizationDTO.getDeptId();
        long parentOrgId = organizationDTO.getParentId();
        TabPbOrgnizeChange tabPbOrgnizeChange = new TabPbOrgnizeChange();
        tabPbOrgnizeChange.setDeptId(orgId);
        tabPbOrgnizeChange.setOldSuperiorId(parentOrgId);
        tabPbOrgnizeChange.setNowSuperiorId(parentOrgId);
        tabPbOrgnizeChange.setOrgnizeCode(organizationDTO.getOrgCode());
        tabPbOrgnizeChange.setOrgnizeName(organizationDTO.getName());
        tabPbOrgnizeChange.setFileNumber(organizationDTO.getFoundedFileNumber());
        tabPbOrgnizeChange.setChangeType(59525L);
        tabPbOrgnizeChange.setChangeDate(new Date());
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbOrgnizeChange);
        orgChangeService.insertSelective(tabPbOrgnizeChange);
    }

    @CacheEvict(value = ORGANIZATION_LIST_FOR_PARENT, allEntries = true)
    @Transactional
    @Override
    public int logicDeleteById(Long orgId) {
        SysDept update = new SysDept();
        update.setDeptId(orgId);
        update.setDelFlag(CommonConstant.STATUS_DEL);
        paddingUpdateRelatedBaseFiled(update);
        SysDept sysDept = tabSysDeptMapper.selectByPrimaryKey(orgId);
        if (CommonConstant.ISPARENTORG.equals(sysDept.getIsParent())) {
            throw new BusinessDataInvalidException("该组织有下级组织，删除失败");
        }
        if (CollectionUtil.isNotEmpty(tabSysUserMapper.selectByOrgIdSelective(orgId))) {
            throw new BusinessDataInvalidException("该组织存在党员，删除失败");
        }
        int judgment = 0;
        //删除已有标签
        judgment += orgTagService.batchUpdateOrgTagByOrgId(orgId);
        judgment += tabSysDeptMapper.updateByPrimaryKeySelective(update);
        //维护上级是否为父组织
        judgment += maintainOrgIsParentOrg(sysDept);
        return judgment;
    }

    @Override
    public OrganizationVO selectOrganizationVOByOrgId(Long orgId) {
        return tabSysDeptMapper.selectOrganizationVOAndUnitsByOrgId(orgId);
    }

    @Transactional
    @CacheEvict(value = ORGANIZATION_LIST_FOR_PARENT, key = "#organizationDTO.getParentId()")
    @Override
    public int updateOrganization(OrganizationDTO organizationDTO) {
        int judgment = 0;

        List<UnitInfoDTO> units = organizationDTO.getUnits();
        if (CollectionUtil.isNotEmpty(units)) {
            List<TabPbUnitInfo> tabPbUnitInfoList =
                    generateTargetListCopyPropertiesAndPaddingBaseField(units, TabPbUnitInfo.class, false);
            List<Long> pendingUnitIdList = tabPbUnitInfoList.stream().map(TabPbUnitInfo::getUnitId).collect(Collectors.toList());
            List<TabPbUnitInfo> dbTabPbUnitInfoList = tabPbUnitInfoMapper.selectByOrgId(organizationDTO.getDeptId());
            if (CollectionUtil.isNotEmpty(dbTabPbUnitInfoList)) { //数据库存在
                List<Long> pendingRemoveUnitIdList = dbTabPbUnitInfoList.stream().filter(tabPbUnitInfo -> !pendingUnitIdList.contains(tabPbUnitInfo.getUnitId()))
                        .map(TabPbUnitInfo::getUnitId)
                        .collect(Collectors.toList());

                if (CollectionUtil.isNotEmpty(pendingRemoveUnitIdList)) {
                    judgment += tabPbUnitInfoMapper.batchLogicDeleteById(pendingRemoveUnitIdList);
                }
            }

            tabPbUnitInfoList.forEach(tabPbUnitInfo -> {
                PaddingBaseFieldUtil.paddingBaseFiled(tabPbUnitInfo);
                if (tabPbUnitInfo.getUnitId() != null && tabPbUnitInfo.getUnitId() > 0) { //修改
                    temporaryUnitId(organizationDTO, tabPbUnitInfo);
                    tabPbUnitInfoMapper.updateByPrimaryKeySelective(tabPbUnitInfo);
                } else { //新增
                    tabPbUnitInfo.setOrgId(organizationDTO.getDeptId());
                    insertUnitInfo(organizationDTO, tabPbUnitInfo);
                }
            });
        }

        SysDept sysDept = generateTargetCopyPropertiesAndPaddingBaseField(organizationDTO, SysDept.class, true);

        //编辑后不是支部就把已有标签删除
        if (!sysDept.ifBranch()) {
            judgment += orgTagService.batchUpdateOrgTagByOrgId(organizationDTO.getDeptId());
        } else {
            if (CommonConstant.ISPARENTORG.equals(tabSysDeptMapper.selectByPrimaryKey(organizationDTO.getDeptId()).getIsParent())) {
                throw new BusinessDataInvalidException("该组织有下级组织，不能修改为支部");
            }
        }

        //维护组织full_path、内部编码及子级的（如有必要）
        modifyFullPathInnerCodeAndSubDeptIfNecessary(sysDept);
        judgment += tabSysDeptMapper.updateByPrimaryKeySelective(sysDept);
        return judgment;
    }

    /**
     * 如有必要,维护 full_path及内部编码 和下级组织 full_path及内部编码
     *
     * @param sysDept
     */
    @Override
    public void modifyFullPathInnerCodeAndSubDeptIfNecessary(SysDept sysDept) {
        Long deptId = sysDept.getDeptId();
        Long parentId = sysDept.getParentId();

        boolean subIfNecessary = false;
        String oldFullPath = null;
        String oldInnerCode = null;
        //从 db 中拿当前组织旧数据
        SysDept dbOldSysDept = tabSysDeptMapper.selectAloneByPrimaryKey(deptId);
        //上级节点有改变
        if (dbOldSysDept != null && !dbOldSysDept.getParentId().equals(sysDept.getParentId())) {
            subIfNecessary = true;
            oldFullPath = dbOldSysDept.getFullPath();
            oldInnerCode = dbOldSysDept.getOrgInnerCode();
        }

        //需要维护
        //获取数据库父组织信息
        SysDept dbParentDept = tabSysDeptMapper.selectAloneByPrimaryKey(parentId);
        //拼接从出新的 full_path
        String newFullPath = String.format("%s,%s", dbParentDept.getFullPath(), deptId);
        sysDept.setFullPath(newFullPath);

        if ((dbOldSysDept != null && StringUtils.isEmpty(dbOldSysDept.getOrgInnerCode()))
                || subIfNecessary) {
            //查出新上级组织子层级中新的内部编码
            String newInnerCode = tabSysDeptMapper.selectSubLevelNewInnerCodeByParentId(parentId);
            sysDept.setOrgInnerCode(newInnerCode);

            if (StringUtils.isNotEmpty(dbOldSysDept.getOrgInnerCode())) {
                tabSysDeptMapper.updateFullPathAndInnerCodeForSubs(oldFullPath, newFullPath, oldInnerCode, newInnerCode, deptId);
            }
        }
    }

    @Override
    public List<HashMap<String, Object>> selectOrganizationSimpleListByCondition(Map<String, Object> conditions, Page page) {
        ReturnEntity returnEntity = sysConfigFeignClient.getConfigurationValue(SystemConfigurationEnum.SHOW_PARTY_GROUP.getItemId());
        if (returnEntity.unOkResp()) {
            throw returnEntity.exception();
        }
        conditions.put("showPartyGroup", CommonConstant.STATUS_EBL.equals(Optional.ofNullable(returnEntity.getResultObj()).orElse(CommonConstant.STATUS_NOEBL)));
        PageHelper.startPage(page);
        return tabSysDeptMapper.selectToMapByCondition(conditions);
    }

    @Override
    public int pushPairOrgIdToDeptTable(Long pairOrgId, Long orgId) {
        return tabSysDeptMapper.pushPairOrgIdToDeptTable(pairOrgId, orgId);
    }

    @Override
    public SysDept selectAloneByPrimaryKey(Long deptId) {
        return tabSysDeptMapper.selectAloneByPrimaryKey(deptId);
    }

    @Override
    @Cacheable(value = ORGANIZATION_COUNT_PARTY_MAN, key = "#orgId", unless = "#result == null")
    public ContainsStatisticsOrganizationVO linkStatisticsData(Long orgId) {
        return tabSysDeptMapper.countOrganizationStatisticsByOrgId(orgId);
    }

    @Override
    public OrganizationPartyBuildingWorkVO selectOrganizationPartyBuildingWorkVOByOrgId(Long deptId) {
        return tabSysDeptMapper.selectOrganizationPartyBuildingWorkVOByOrgId(deptId);
    }

    @Override
    public int updateByPrimaryKeySelective(SysDept sysDept) {
        return tabSysDeptMapper.updateByPrimaryKeySelective(sysDept);
    }

    /**
     * 暂存单位ID
     *
     * @param organizationDTO
     * @param tabPbUnitInfo
     */
    private void temporaryUnitId(OrganizationDTO organizationDTO, TabPbUnitInfo tabPbUnitInfo) {
        //主单位, 保留ID
        if (tabPbUnitInfo.getIfMaster() == 1) {
            organizationDTO.setUnitId(tabPbUnitInfo.getUnitId());
            organizationDTO.setUnitName(tabPbUnitInfo.getUnitName());
        }
    }

    /**
     * 维护组织是否为父级组织
     *
     * @param sysDept
     * @return
     */
    public int maintainOrgIsParentOrg(SysDept sysDept) {
        SysDept parentSysDept = tabSysDeptMapper.selectByPrimaryKey(sysDept.getParentId());
        if (tabSysDeptMapper.checkOrgIsParentOrg(sysDept.getParentId())) {
            if (!CommonConstant.ISPARENTORG.equals(parentSysDept.getIsParent())) {
                parentSysDept.setIsParent(CommonConstant.ISPARENTORG);
                paddingUpdateRelatedBaseFiled(parentSysDept);
                return tabSysDeptMapper.updateByPrimaryKeySelective(parentSysDept);
            }
        } else {
            if (!CommonConstant.ISNOTPARENTORG.equals(parentSysDept.getIsParent())) {
                parentSysDept.setIsParent(CommonConstant.ISNOTPARENTORG);
                paddingUpdateRelatedBaseFiled(parentSysDept);
                return tabSysDeptMapper.updateByPrimaryKeySelective(parentSysDept);
            }
        }
        return 0;
    }

    /**
     * 新增单位信息
     *
     * @param organizationDTO
     * @param tabPbUnitInfo
     */
    private void insertUnitInfo(OrganizationDTO organizationDTO, TabPbUnitInfo tabPbUnitInfo) {
        //关联组织ID
        tabPbUnitInfo.setOrgId(organizationDTO.getDeptId());
        PaddingBaseFieldUtil.paddingBaseFiled(tabPbUnitInfo);
        tabPbUnitInfoMapper.insertSelective(tabPbUnitInfo);

        temporaryUnitId(organizationDTO, tabPbUnitInfo);
    }

    @Override
    public boolean checkOrgIsExists(Long deptId) {
        return tabSysDeptMapper.selectByPrimaryKey(deptId) != null;
    }

    @Override
    public List<OrganizationPositionVO> selectOrganizationPositionVOByCondition(Map<String, Object> conditions, Page page) {
        ReturnEntity returnEntity = sysConfigFeignClient.getConfigurationValue(SystemConfigurationEnum.SHOW_PARTY_GROUP.getItemId());
        if (returnEntity.unOkResp()) {
            throw returnEntity.exception();
        }
        conditions.put("showPartyGroup", CommonConstant.STATUS_EBL.equals(Optional.ofNullable(returnEntity.getResultObj()).orElse(CommonConstant.STATUS_NOEBL)));
        PageHelper.startPage(page);
        return tabSysDeptMapper.selectOrganizationPositionVOByCondition(conditions);
    }

    @Override
    public OrganizationPositionVO selectOrganizationPositionVOAndAttachmentsById(Long orgId) {
        return tabSysDeptMapper.selectOrganizationPositionVOAndAttachmentsById(orgId);
    }

    @Transactional
    @Override
    public int updateOrganizationPosition(OrganizationPositionDTO organizationPositionDTO) {
        boolean exist = tabSysDeptMapper.checkIsExistByOrgId(organizationPositionDTO.getDeptId());
        if (!exist) {
            throw new BusinessDataNotFoundException("组织数据不存在");
        }
        SysDept sysDept = generateTargetCopyPropertiesAndPaddingBaseField(organizationPositionDTO, SysDept.class, true);
        int judgment = tabSysDeptMapper.updateByPrimaryKeySelective(sysDept);
        if (judgment > 0) {
            judgment += iTabPbAttachmentService.intelligentOperation(organizationPositionDTO.getAttachments(),
                    organizationPositionDTO.getDeptId(), AttachmentType.ORG_POSITION);
        }
        return judgment;
    }

    public List<OrganizationVO> calculationComplete(List<OrganizationVO> list) {
        list.forEach(org -> {
            int tool = 100;
            if (ObjectUtils.isEmpty(org.getIsCity())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(org.getIsTeam())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(org.getPostCode())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(org.getCommunityAddr())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(org.getContactNumber())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(org.getContactor())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(org.getOrgnizeMasterId())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(org.getFoundedDate())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(org.getFoundedFileNumber())) {
                tool -= COMPLETE_SEED;
            }
            if (ObjectUtils.isEmpty(org.getFoundedReason())) {
                tool -= COMPLETE_SEED;
            }
            org.setComplete(tool);
        });
        return list;
    }
}
