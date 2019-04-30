package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.exception.BusinessDataCheckFailException;
import com.egovchina.partybuilding.common.exception.BusinessDataInvalidException;
import com.egovchina.partybuilding.common.util.AttachmentType;
import com.egovchina.partybuilding.partybuild.dto.OrgChangeDTO;
import com.egovchina.partybuilding.partybuild.entity.SysDept;
import com.egovchina.partybuilding.partybuild.entity.TabPbOrgnizeChange;
import com.egovchina.partybuilding.partybuild.repository.TabPbOrgnizeChangeMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysDeptMapper;
import com.egovchina.partybuilding.partybuild.repository.TabSysUserMapper;
import com.egovchina.partybuilding.partybuild.service.ITabPbAttachmentService;
import com.egovchina.partybuilding.partybuild.service.ITabSysDictService;
import com.egovchina.partybuilding.partybuild.service.OrgChangeService;
import com.egovchina.partybuilding.partybuild.service.TabSysDeptService;
import com.egovchina.partybuilding.partybuild.vo.DirectPartyMemberVO;
import com.egovchina.partybuilding.partybuild.vo.OrgChangeVO;
import com.github.pagehelper.PageHelper;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.egovchina.partybuilding.common.util.BeanUtil.copyPropertiesIgnoreNullAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingBaseFiled;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;
import static com.egovchina.partybuilding.common.util.UserContextHolder.currentUser;

/**
 * 描述:
 * 组织调整实现类
 *
 * @outhor wuyunjie
 * @create 2018-12-02 10:40
 */
@Service("orgChangeService")
public class OrgChangeServiceImpl implements OrgChangeService {

    @Autowired
    private TabPbOrgnizeChangeMapper tabPbOrgnizeChangeMapper;

    @Autowired
    private TabSysDeptMapper tabSysDeptMapper;

    @Autowired
    private ITabSysDictService tabSysDictService;

    @Autowired
    private TabSysDeptService tabSysDeptService;

    @Autowired
    private TabSysUserMapper tabSysUserMapper;

    @Autowired
    private ITabPbAttachmentService iTabPbAttachmentService;

    @Override
    public OrgChangeVO selectOrgChangeByDeptIdOrderTime(Long deptId, Long changeType) {
        return tabPbOrgnizeChangeMapper.selectOrgChangeByDeptIdOrderTime(deptId, changeType);
    }

    /**
     * 添加组织调整记录
     *
     * @param tabPbOrgnizeChange
     * @return
     */
    @Override
    @Transactional
    public int insertOrgChange(TabPbOrgnizeChange tabPbOrgnizeChange) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(tabPbOrgnizeChange.getDeptId().intValue());
        SysDept superiorOrganization = tabSysDeptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getNowSuperiorId());
        if (superiorOrganization != null) {
            sysDept.setParentId(tabPbOrgnizeChange.getNowSuperiorId().intValue());
        } else {
            throw new BusinessDataCheckFailException("上级组织不存在");
        }
        int parentId = tabSysDeptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getDeptId()).getParentId();
        if (tabPbOrgnizeChange.getNowSuperiorId() == parentId) {
            throw new BusinessDataCheckFailException("上级组织未改变");
        }
        SysDept dept = tabSysDeptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getDeptId());
        if (dept != null) {
            if (dept.getUnitState() != null && dept.getUnitState() == 59139L) {
                sysDept.setUnitId(superiorOrganization.getUnitId());
                sysDept.setUnitName(superiorOrganization.getUnitName());
            }
        }
        sysDept.setOrgCode(tabPbOrgnizeChange.getOrgnizeCode());
        sysDept.setFoundedFileNumber(tabPbOrgnizeChange.getFileNumber());
        paddingUpdateRelatedBaseFiled(sysDept);
        tabSysDeptService.modifyFullPathAndSubDeptIfNecessary(sysDept);
        return tabSysDeptMapper.updateByPrimaryKeySelective(sysDept);
    }

    @PaddingBaseField
    @Override
    public int insertSelective(TabPbOrgnizeChange tabPbOrgnizeChange) {
        return tabPbOrgnizeChangeMapper.insertSelective(tabPbOrgnizeChange);
    }

    /**
     * 组织变动 (分为组织更名 ZZGM, 组织撤销 ZZCX, 组织恢复 ZZHF， 组织调整 ZZTZ，整建制转移 ZJZZY)
     *
     * @param orgChangeDTO
     * @return
     */
    @Transactional
    @Override
    @PaddingBaseField
    public int addOrgChange(OrgChangeDTO orgChangeDTO) {
        // 我改不了, 只能转int了
        if (Objects.requireNonNull(currentUser()).getDeptId() != null) {
            if (orgChangeDTO.getDeptId() == Objects.requireNonNull(currentUser()).getDeptId().longValue()) {
                throw new BusinessDataCheckFailException("当前组织不可调整自身组织");
            }
        } else {
            if (orgChangeDTO.getDeptId() == Objects.requireNonNull(currentUser()).getManageDeptId().longValue()) {
                throw new BusinessDataCheckFailException("当前组织不可调整自身组织");
            }
        }

        var dict = this.tabSysDictService.selectById(orgChangeDTO.getChangeType().intValue());
        if (dict == null) {
            throw new BusinessDataInvalidException("数据字典不存在");
        }

        if (tabSysDeptMapper.selectByPrimaryKey(orgChangeDTO.getDeptId()) == null) {
            throw new BusinessDataInvalidException("组织不存在");
        }
        //restful 属性拷贝，填充基本字段
        TabPbOrgnizeChange tabPbOrgnizeChange = new TabPbOrgnizeChange();
        copyPropertiesIgnoreNullAndPaddingBaseField(orgChangeDTO, tabPbOrgnizeChange, true);
        if (dict.getValue().equals("ZZGM")) {
            this.orgRename(tabPbOrgnizeChange);
        } else if (dict.getValue().equals("ZZCX")) {
            this.orgRestoreOrRevoke(tabPbOrgnizeChange, "0", 59123L);
        } else if (dict.getValue().equals("ZZHF")) {
            this.orgRestoreOrRevoke(tabPbOrgnizeChange, "1", 59122L);
        } else if (dict.getValue().equals("ZZTZ") || dict.getValue().equals("ZJZZY")) {
            this.insertOrgChange(tabPbOrgnizeChange);
        }
        paddingBaseFiled(tabPbOrgnizeChange);
        int count = 0;
        count += this.tabPbOrgnizeChangeMapper.insertSelective(tabPbOrgnizeChange);
        count += this.iTabPbAttachmentService.intelligentOperation(orgChangeDTO.getAttachments(),
                tabPbOrgnizeChange.getChangeId(), AttachmentType.ORG_CHANGE);
        return count;
    }

    /**
     * 组织更名
     *
     * @param org
     * @return
     */
    private Integer orgRename(TabPbOrgnizeChange org) {
        if (this.tabSysDeptMapper.checkNameOrShortName(org.getOrgnizeName()) > 0) {
            throw new BusinessDataInvalidException("组织名称已存在");
        }

        if (this.tabSysDeptMapper.checkNameOrShortName(org.getShortName()) > 0) {
            throw new BusinessDataInvalidException("组织简称已存在");
        }

        var newDept = new SysDept();
        newDept.setDeptId(org.getDeptId().intValue());
        newDept.setName(org.getOrgnizeName());
        newDept.setOrgShortName(org.getShortName());
        int count = this.tabSysDeptMapper.updateByPrimaryKeySelective(newDept);
        return count;
    }

    /**
     * 组织恢复/撤销
     *
     * @param org 变更信息
     * @return
     */
    private int orgRestoreOrRevoke(TabPbOrgnizeChange org, String eblFlag, Long orgStatus) {
        if (orgStatus == 59123L) {
            SysDept sysDept = this.tabSysDeptMapper.selectByPrimaryKey(org.getDeptId());
            if (sysDept.getIsParent() == 1) {
                throw new BusinessDataInvalidException("该组织存在下级组织，撤消失败");
            }
            List<DirectPartyMemberVO> directPartyMemberVOS = tabSysUserMapper.selectDirectPartyMemberVOByDeptId(org.getDeptId());
            if (directPartyMemberVOS.size() > 0) {
                throw new BusinessDataInvalidException("该组织存在党员，撤消失败");
            }
        }
        var newDept = new SysDept();
        newDept.setDeptId(org.getDeptId().intValue());
        newDept.setEblFlag(eblFlag);
        newDept.setOrgStatus(orgStatus);
        return this.tabSysDeptMapper.updateByPrimaryKeySelective(newDept);
    }

    @Override
    public List<OrgChangeVO> selectOrgChangeList(Long deptId, Page page) {
        PageHelper.startPage(page);
        return tabPbOrgnizeChangeMapper.selectOrgChangeVOList(deptId);
    }
}
