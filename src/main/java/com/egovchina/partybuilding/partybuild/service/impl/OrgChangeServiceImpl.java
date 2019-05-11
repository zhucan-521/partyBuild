package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysDept;
import com.egovchina.partybuilding.common.enums.OrgChangeTypeEnum;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.common.util.CommonConstant;
import com.egovchina.partybuilding.common.util.UserContextHolder;
import com.egovchina.partybuilding.partybuild.dto.OrgChangeDTO;
import com.egovchina.partybuilding.partybuild.entity.SysDict;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgnizeChangeMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.OrgChangeService;
import com.egovchina.partybuilding.partybuild.service.OrganizationService;
import com.egovchina.partybuilding.partybuild.service.SysDictService;
import com.egovchina.partybuilding.partybuild.vo.DirectPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.OrgChangeVO;
import com.github.pagehelper.PageHelper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.egovchina.partybuilding.common.util.BeanUtil.copyPropertiesIgnoreNullAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingBaseFiled;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;
import static com.egovchina.partybuilding.common.util.RedisKeyConstant.ORGANIZATION_LIST_FOR_PARENT;

/**
 * 描述:
 * 组织调整实现类
 *
 * @outhor wuyunjie
 * @create 2018-12-02 10:40
 */
@Service("orgChangeService")
public class OrgChangeServiceImpl implements OrgChangeService {

    //组织撤消状态
    private final Long REVOKE = 59123L;

    @Autowired
    private TabPbOrgnizeChangeMapper tabPbOrgnizeChangeMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private SysDictService tabSysDictService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Override
    public OrgChangeVO selectOrgChangeByDeptIdOrderTime(Long deptId, Long changeType) {
        return tabPbOrgnizeChangeMapper.selectOrgChangeByDeptIdOrderTime(deptId, changeType);
    }

    @Transactional
    @Override
    public int orgChangeSuperiorOrg(TabPbOrgnizeChange tabPbOrgnizeChange, SysDept sysDept) {

        SysDept superiorOrganization = tabSysDeptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getNowSuperiorId());
        if (superiorOrganization == null) {
            throw new BusinessDataCheckFailException("上级组织不存在");
        }

        if (tabPbOrgnizeChange.getNowSuperiorId().equals(sysDept.getParentId())) {
            throw new BusinessDataCheckFailException("上级组织未改变");
        }
        SysDept newDept = new SysDept();
        //组织所在单位类型 上级党组织相同
        Long unitType = 59139L;
        if (sysDept.getUnitState() != null && Objects.equals(sysDept.getUnitState(), unitType)) {
            newDept.setUnitId(superiorOrganization.getUnitId());
            newDept.setUnitName(superiorOrganization.getUnitName());
        }
        newDept.setDeptId(tabPbOrgnizeChange.getDeptId());
        newDept.setParentId(tabPbOrgnizeChange.getNowSuperiorId());
        newDept.setOrgCode(tabPbOrgnizeChange.getOrgnizeCode());
        newDept.setFoundedFileNumber(tabPbOrgnizeChange.getFileNumber());
        paddingUpdateRelatedBaseFiled(newDept);
        organizationService.modifyFullPathAndSubDeptIfNecessary(newDept);
        return tabSysDeptMapper.updateByPrimaryKeySelective(newDept);
    }

    @PaddingBaseField
    @Override
    public int insertSelective(TabPbOrgnizeChange tabPbOrgnizeChange) {
        return tabPbOrgnizeChangeMapper.insertSelective(tabPbOrgnizeChange);
    }

    /**
     * 组织变动 (分为组织更名 ZZGM, 组织撤销 ZZCX, 组织恢复 ZZHF， 组织调整 ZZTZ， 其他调整 QTTZ， 整建制转移 ZJZZY)
     *
     * @param orgChangeDTO 变动TDO
     * @return int
     */
    @Caching(evict = {
            @CacheEvict(value = ORGANIZATION_LIST_FOR_PARENT, key = "#orgChangeDTO.getNowSuperiorId()"
                    , condition = "#orgChangeDTO.changeType != 59567L"),
            @CacheEvict(value = ORGANIZATION_LIST_FOR_PARENT, key = "#orgChangeDTO.getOldSuperiorId()"
                    , condition = "#orgChangeDTO.changeType != 59567L && #orgChangeDTO.getOldSuperiorId() != null")
    })
    @Transactional
    @Override
    public int addOrgChange(OrgChangeDTO orgChangeDTO) {
        if (orgChangeDTO.getDeptId().equals(UserContextHolder.getOrgId())) {
            throw new BusinessDataCheckFailException("当前组织不可调整自身组织");
        }

        SysDict dict = this.tabSysDictService.selectById(orgChangeDTO.getChangeType());
        if (dict == null) {
            throw new BusinessDataInvalidException("数据字典不存在");
        }

        SysDept sysDept = tabSysDeptMapper.selectByPrimaryKey(orgChangeDTO.getDeptId());
        if (sysDept == null) {
            throw new BusinessDataInvalidException("组织不存在");
        }
        //restful 属性拷贝，填充基本字段
        TabPbOrgnizeChange tabPbOrgnizeChange = new TabPbOrgnizeChange();
        copyPropertiesIgnoreNullAndPaddingBaseField(orgChangeDTO, tabPbOrgnizeChange, true);
        //组织正常状态
        Long normal = 59122L;
        switch (Objects.requireNonNull(OrgChangeTypeEnum.getType(dict.getValue()))) {
            case ORG_RENAME:
                ((OrgChangeService) AopContext.currentProxy()).orgRename(tabPbOrgnizeChange, sysDept);
                break;
            case ORG_REVOKE:
                ((OrgChangeService) AopContext.currentProxy()).orgRestoreOrRevoke(
                        tabPbOrgnizeChange, sysDept, CommonConstant.STATUS_NOEBL, REVOKE);
                break;
            case ORG_RESTORE:
                ((OrgChangeService) AopContext.currentProxy()).orgRestoreOrRevoke(
                        tabPbOrgnizeChange, sysDept, CommonConstant.STATUS_EBL, normal);
                break;
            case OTHER_CHANGE:
                break;
            case ORG_ADJUST:
            case ORG_SHIFT:
                ((OrgChangeService) AopContext.currentProxy()).orgChangeSuperiorOrg(tabPbOrgnizeChange, sysDept);
                break;
            default:
                throw new BusinessDataInvalidException("组织变动类型不存在");
        }
        paddingBaseFiled(tabPbOrgnizeChange);
        int count = 0;
        count += insertSelective(tabPbOrgnizeChange);
        count += this.iTabPbAttachmentService.intelligentOperation(orgChangeDTO.getAttachments(),
                tabPbOrgnizeChange.getChangeId(), AttachmentType.ORG_CHANGE);
        return count;
    }

    @Transactional
    public int orgRename(TabPbOrgnizeChange org, SysDept sysDept) {
        if (sysDept.getName().equals(org.getOrgnizeName())
                && sysDept.getOrgShortName().equals(org.getShortName())) {
            throw new BusinessDataInvalidException("组织名称未改变");
        }
        Boolean checkOrgRename =
                Optional.ofNullable(
                        tabSysDeptMapper.checkNameOrShortName(
                                org.getOrgnizeName(), org.getDeptId())).orElse(false);
        if (checkOrgRename) {
            throw new BusinessDataInvalidException("组织名称已存在");
        }
        Boolean checkOrgShortRename =
                Optional.ofNullable(
                        tabSysDeptMapper.checkNameOrShortName(
                                org.getShortName(), org.getDeptId())).orElse(false);
        if (checkOrgShortRename) {
            throw new BusinessDataInvalidException("组织简称已存在");
        }
        SysDept newDept = new SysDept();
        newDept.setDeptId(org.getDeptId());
        newDept.setName(org.getOrgnizeName());
        newDept.setOrgShortName(org.getShortName());
        paddingUpdateRelatedBaseFiled(newDept);
        return this.tabSysDeptMapper.updateByPrimaryKeySelective(newDept);
    }

    @Transactional
    public int orgRestoreOrRevoke(TabPbOrgnizeChange org, SysDept sysDept, String eblFlag, Long orgStatus) {
        if (REVOKE.equals(orgStatus)) {
            if (sysDept.getIsParent() == 1) {
                throw new BusinessDataInvalidException("该组织存在下级组织，撤消失败");
            }
            List<DirectPartyMemberVO> directPartyMemberVOS =
                    tabSysUserMapper.selectDirectPartyMemberVOByDeptId(org.getDeptId());
            if (directPartyMemberVOS.size() > 0) {
                throw new BusinessDataInvalidException("该组织存在党员，撤消失败");
            }
        }
        SysDept newDept = new SysDept();
        newDept.setDeptId(org.getDeptId());
        newDept.setEblFlag(eblFlag);
        newDept.setOrgStatus(orgStatus);
        paddingUpdateRelatedBaseFiled(newDept);
        return this.tabSysDeptMapper.updateByPrimaryKeySelective(newDept);
    }

    @Override
    public List<OrgChangeVO> selectOrgChangeList(Long deptId, Page page) {
        PageHelper.startPage(page);
        return tabPbOrgnizeChangeMapper.selectOrgChangeVOList(deptId);
    }
}
