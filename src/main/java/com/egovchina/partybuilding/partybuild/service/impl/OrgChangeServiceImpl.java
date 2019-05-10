package com.egovchina.partybuilding.partybuild.service.impl;

import com.egovchina.partybuilding.common.config.PaddingBaseField;
import com.egovchina.partybuilding.common.entity.Page;
import com.egovchina.partybuilding.common.entity.SysDept;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.egovchina.partybuilding.common.util.BeanUtil.copyPropertiesIgnoreNullAndPaddingBaseField;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingBaseFiled;
import static com.egovchina.partybuilding.common.util.PaddingBaseFieldUtil.paddingUpdateRelatedBaseFiled;

/**
 * 描述:
 * 组织调整实现类
 *
 * @outhor wuyunjie
 * @create 2018-12-02 10:40
 */
@Service("orgChangeService")
public class OrgChangeServiceImpl implements OrgChangeService {

    //组织所在单位类型 上级党组织相同
    private final Long UNIT_SAME = 59139L;

    //组织撤消字典
    private final Long REVOKE = 59123L;

    //组织恢复字典
    private final Long RESTORE = 59123L;

    //组织更名 ZZGM
    private final String ORG_RENAME = "ZZGM";

    //组织撤销 ZZCX
    private final String ORG_REVOKE = "ZZCX";

    //组织恢复 ZZHF
    private final String ORG_RESTORE = "ZZHF";

    //组织调整 ZZTZ
    private final String ORG_ADJUST = "ZZTZ";

    //整建制转移 ZJZZY
    private final String ORG_SHIFT = "ZJZZY";

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

    /**
     * 添加组织调整记录
     *
     * @param tabPbOrgnizeChange
     * @return
     */
    @Transactional
    @Override
    public int insertOrgChange(TabPbOrgnizeChange tabPbOrgnizeChange) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(tabPbOrgnizeChange.getDeptId());
        SysDept superiorOrganization = tabSysDeptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getNowSuperiorId());
        if (superiorOrganization == null) {
            throw new BusinessDataCheckFailException("上级组织不存在");
        }
        sysDept.setParentId(tabPbOrgnizeChange.getNowSuperiorId());
        long parentId = tabSysDeptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getDeptId()).getParentId();
        if (tabPbOrgnizeChange.getNowSuperiorId() == parentId) {
            throw new BusinessDataCheckFailException("上级组织未改变");
        }
        SysDept dept = tabSysDeptMapper.selectByPrimaryKey(tabPbOrgnizeChange.getDeptId());
        if (dept != null) {
            if (dept.getUnitState() != null && Objects.equals(dept.getUnitState(), UNIT_SAME)) {
                sysDept.setUnitId(superiorOrganization.getUnitId());
                sysDept.setUnitName(superiorOrganization.getUnitName());
            }
        }
        sysDept.setOrgCode(tabPbOrgnizeChange.getOrgnizeCode());
        sysDept.setFoundedFileNumber(tabPbOrgnizeChange.getFileNumber());
        paddingUpdateRelatedBaseFiled(sysDept);
        organizationService.modifyFullPathAndSubDeptIfNecessary(sysDept);
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
    public int addOrgChange(OrgChangeDTO orgChangeDTO) {
        if (orgChangeDTO.getDeptId().equals(UserContextHolder.getOrgId())) {
            throw new BusinessDataCheckFailException("当前组织不可调整自身组织");
        }

        SysDict dict = this.tabSysDictService.selectById(orgChangeDTO.getChangeType());
        if (dict == null) {
            throw new BusinessDataInvalidException("数据字典不存在");
        }

        if (!tabSysDeptMapper.checkIsExistByOrgId(orgChangeDTO.getDeptId())) {
            throw new BusinessDataInvalidException("组织不存在");
        }
        //restful 属性拷贝，填充基本字段
        TabPbOrgnizeChange tabPbOrgnizeChange = new TabPbOrgnizeChange();
        copyPropertiesIgnoreNullAndPaddingBaseField(orgChangeDTO, tabPbOrgnizeChange, true);
        switch (dict.getValue()) {
            case ORG_RENAME:
                this.orgRename(tabPbOrgnizeChange);
                break;
            case ORG_REVOKE:
                this.orgRestoreOrRevoke(tabPbOrgnizeChange, CommonConstant.STATUS_NOEBL, REVOKE);
                break;
            case ORG_RESTORE:
                this.orgRestoreOrRevoke(tabPbOrgnizeChange, CommonConstant.STATUS_EBL, RESTORE);
                break;
            case ORG_ADJUST:
            case ORG_SHIFT:
                this.insertOrgChange(tabPbOrgnizeChange);
                break;
            default:
                throw new BusinessDataInvalidException("组织变动类型不存在");
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
    private int orgRename(TabPbOrgnizeChange org) {
        if (tabSysDeptMapper.checkNameOrShortName(org.getOrgnizeName(), org.getDeptId()) > 0) {
            throw new BusinessDataInvalidException("组织名称已存在");
        }

        SysDept newDept = new SysDept();
        newDept.setDeptId(org.getDeptId());
        newDept.setName(org.getOrgnizeName());
        newDept.setOrgShortName(org.getShortName());
        return this.tabSysDeptMapper.updateByPrimaryKeySelective(newDept);
    }

    /**
     * 组织恢复/撤销
     *
     * @param org 变更信息
     * @return
     */
    private int orgRestoreOrRevoke(TabPbOrgnizeChange org, String eblFlag, Long orgStatus) {
        if (Objects.equals(orgStatus, REVOKE)) {
            SysDept sysDept = this.tabSysDeptMapper.selectByPrimaryKey(org.getDeptId());
            if (sysDept.getIsParent() == 1) {
                throw new BusinessDataInvalidException("该组织存在下级组织，撤消失败");
            }
            List<DirectPartyMemberVO> directPartyMemberVOS = tabSysUserMapper.selectDirectPartyMemberVOByDeptId(org.getDeptId());
            if (directPartyMemberVOS.size() > 0) {
                throw new BusinessDataInvalidException("该组织存在党员，撤消失败");
            }
        }
        SysDept newDept = new SysDept();
        newDept.setDeptId(org.getDeptId());
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
